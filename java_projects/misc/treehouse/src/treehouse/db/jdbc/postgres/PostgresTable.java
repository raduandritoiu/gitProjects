package treehouse.db.jdbc.postgres;

import java.sql.*;
import java.util.*;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.projecthaystack.*;

import treehouse.db.*;
import treehouse.db.event.*;
import treehouse.db.jdbc.*;
import treehouse.haystack.*;
import treehouse.haystack.io.*;

/**
  * PostgresTable is a Table that is persisted into a Postgres Database.
  */
class PostgresTable extends Table
{
    PostgresTable(PostgresDatabase db, String name)
    {
        super(db, name);
        this.db = db;

        this.ddlRecs = new String[DDL_RECS.length];
        this.ddlRefs = new String[DDL_REFS.length];

        for (int i = 0; i < DDL_RECS.length; i++) ddlRecs[i] = DDL_RECS[i].replace("$", name + "_");
        for (int i = 0; i < DDL_REFS.length; i++) ddlRefs[i] = DDL_REFS[i].replace("$", name + "_");

        sqlSelectSize = SELECT_SIZE.replace("$", name + "_");
        sqlSelectRec  = SELECT_REC.replace("$",  name + "_");
        sqlSelectDis  = SELECT_DIS.replace("$",  name + "_");
        sqlInsertRec  = INSERT_REC.replace("$",  name + "_");
        sqlInsertRef  = INSERT_REF.replace("$",  name + "_");
        sqlUpdateRec  = UPDATE_REC.replace("$",  name + "_");
        sqlDeleteRec  = DELETE_REC.replace("$",  name + "_");
        sqlDeleteRef  = DELETE_REF.replace("$",  name + "_");

        this.disCache = CacheBuilder.newBuilder()
            .maximumSize(DIS_CACHE_SIZE)
            .build(new DisLoader());
    }

    /**
      * Implementation hook for open().
      */
    protected void doOpen()
    {
        try
        {
            try (Connection conn = db.getConnection())
            {
                JdbcDatabase.ensureTable(conn, getName() + "_recs", ddlRecs);
                JdbcDatabase.ensureTable(conn, getName() + "_refs", ddlRefs);
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
      * Implementation hook for close().
      */
    protected void doClose()
    {
    }

    /**
      * Hook for processing a record that is being scanned during open().
      */
    protected void openRecord(HDict record)
    {
    }

////////////////////////////////////////////////////////////////
// Querying
////////////////////////////////////////////////////////////////

    /**
      * Implementation hook for readCount().
      */
    protected int doReadSize()
    {
        try
        {
            try (Connection conn = db.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlSelectSize))
            {
                rs.next();
                return rs.getInt(1);
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
      * Implementation hook for readById().
      */
    protected HDict doReadById(HRef id)
    {
        LOG.trace("readById " + id);

        try
        {
            try (
                Connection conn = db.getConnection();
                PreparedStatement read = conn.prepareStatement(sqlSelectRec))
            {
                HDict record = readRec(id, read);
                return computeDis(record);
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
      * Implementation hook for read().
      */
    protected HDict doRead(Filter filter)
    {
        try
        {
            String sql = SqlBuilder.buildSelect(getName(), filter, "r0.doc", -1, -1);
            LOG.trace("read " + filter + ", " + sql);

            try (
                Connection conn = db.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql))
            {
                if (rs.next())
                {
                    HDict record = (HDict) JsonCodec.parse(rs.getString(1));
                    return computeDis(record);
                }
                else
                    throw new RuntimeException("There is no record matching filter '" + filter + "'.");
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
      * Implementation hook for readAll().
      */
    protected Cursor doReadAll(Filter filter, int skip, int limit)
    {
        try
        {
            long begin = System.currentTimeMillis();
            String sql = SqlBuilder.buildSelect(getName(), filter, "r0.doc", skip, limit);
            LOG.info("readAll begin: " + 
                filter + ", " + skip + ", " + limit + ", " + 
                sql.replace('\n', ' '));

            Connection conn = db.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            long end = System.currentTimeMillis();
            long elapsed = end - begin;
            LOG.info("readAll end: " + elapsed + "ms for " + filter);

            return new PostgresCursor(this, conn, stmt, rs);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

////////////////////////////////////////////////////////////////
// Insert, Update, Delete
////////////////////////////////////////////////////////////////

    /**
      * Implementation hook for insert().
      */
    protected InsertEvent doInsert(HDict record)
    {
        LOG.trace("insert " + record.id());

        try
        {
            Connection conn = db.getConnection();
            try
            {
                conn.setAutoCommit(false);

                try (PreparedStatement insRec = conn.prepareStatement(sqlInsertRec);
                     PreparedStatement insRef = conn.prepareStatement(sqlInsertRef))
                {
                    insertRec(record, insRec);
                    insertRefs(record, insRef);
                }

                conn.commit();
                return new InsertEvent(this, record);
            }
            catch (SQLException e)
            {
                conn.rollback();
                throw e;
            }
            finally
            {
                conn.close();
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
      * Implementation hook for batchInsert().
      */
    protected List<InsertEvent> doBatchInsert(Iterator<HDict> records)
    {
        LOG.trace("batchInsert");

        try
        {
            Connection conn = db.getConnection();
            try
            {
                conn.setAutoCommit(false);
                List<InsertEvent> events = new ArrayList<>();

                try (PreparedStatement insRec = conn.prepareStatement(sqlInsertRec);
                     PreparedStatement insRef = conn.prepareStatement(sqlInsertRef))
                {
                    while (records.hasNext())
                    {
                        HDict record = records.next();
                        insertRec(record, insRec);
                        insertRefs(record, insRef);
                        events.add(new InsertEvent(this, record));
                    }
                }

                conn.commit();
                return events;
            }
            catch (SQLException e)
            {
                conn.rollback();
                throw e;
            }
            finally
            {
                conn.close();
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
      * Implementation hook for updateById().
      */
    protected UpdateEvent doUpdateById(HRef id, Diff diff)
    {
        LOG.trace("updateById " + id + ", " + diff);

        try
        {
            Connection conn = db.getConnection();
            try
            {
                conn.setAutoCommit(false);
                UpdateEvent event;

                try (
                    PreparedStatement read   = conn.prepareStatement(sqlSelectRec);
                    PreparedStatement updRec = conn.prepareStatement(sqlUpdateRec);
                    PreparedStatement delRef = conn.prepareStatement(sqlDeleteRef);
                    PreparedStatement insRef = conn.prepareStatement(sqlInsertRef))
                {
                    HDict oldRec = readRec(id, read);
                    HDict newRec = diff.apply(oldRec);
                    event = new UpdateEvent(this, newRec, diff, oldRec);

                    updRec.setObject(1, JsonCodec.encode(newRec, false), Types.OTHER);
                    updRec.setString(2, dis(newRec));
                    updRec.setString(3, id.toZinc());
                    updRec.execute();

                    delRef.setString(1, id.toZinc());
                    delRef.execute();

                    insertRefs(newRec, insRef);

                    disCache.invalidate(id);
                }

                conn.commit();
                return event;
            }
            catch (SQLException e)
            {
                conn.rollback();
                throw e;
            }
            finally
            {
                conn.close();
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
      * Implementation hook for updateAll().
      */
    protected List<UpdateEvent> doUpdateAll(Filter filter, Diff diff)
    {
        LOG.trace("updateAll " + filter + ", " + diff);

        try
        {
            Connection conn = db.getConnection();
            try
            {
                conn.setAutoCommit(false);
                List<UpdateEvent> events = new ArrayList<>();

                String sql = SqlBuilder.buildSelect(getName(), filter, "r0.doc", -1, -1);

                try (
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    PreparedStatement updRec = conn.prepareStatement(sqlUpdateRec);
                    PreparedStatement delRef = conn.prepareStatement(sqlDeleteRef);
                    PreparedStatement insRef = conn.prepareStatement(sqlInsertRef))
                {
                    while (rs.next())
                    {
                        HDict oldRec = (HDict) JsonCodec.parse(rs.getString(1));
                        HRef id = oldRec.id();
                        HDict newRec = diff.apply(oldRec);

                        events.add(new UpdateEvent(this, newRec, diff, oldRec));

                        updRec.setObject(1, JsonCodec.encode(newRec, false), Types.OTHER);
                        updRec.setString(2, dis(newRec));
                        updRec.setString(3, id.toZinc());
                        updRec.execute();

                        delRef.setString(1, id.toZinc());
                        delRef.execute();

                        insertRefs(newRec, insRef);

                        disCache.invalidate(id);
                    }
                }
 
                conn.commit();
                return events;
            }
            catch (SQLException e)
            {
                conn.rollback();
                throw e;
            }
            finally
            {
                conn.close();
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
      * Implementation hook for deleteById().
      */
    protected DeleteEvent doDeleteById(HRef id)
    {
        LOG.trace("deleteById " + id);

        try
        {
            Connection conn = db.getConnection();
            try
            {
                conn.setAutoCommit(false);
                DeleteEvent event;

                try (
                    PreparedStatement read = conn.prepareStatement(sqlSelectRec);
                    PreparedStatement delRec = conn.prepareStatement(sqlDeleteRec);
                    PreparedStatement delRef = conn.prepareStatement(sqlDeleteRef))
                {
                    HDict record = readRec(id, read);
                    event = new DeleteEvent(this, record);

                    delRec.setString(1, id.toZinc());
                    delRec.execute();

                    delRef.setString(1, id.toZinc());
                    delRef.execute();
                }

                conn.commit();
                return event;
            }
            catch (SQLException e)
            {
                conn.rollback();
                throw e;
            }
            finally
            {
                conn.close();
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
      * Implementation hook for deleteAll().
      */
    protected List<DeleteEvent> doDeleteAll(Filter filter)
    {
        LOG.trace("deleteAll " + filter);

        try
        {
            Connection conn = db.getConnection();
            try
            {
                conn.setAutoCommit(false);
                List<DeleteEvent> events = new ArrayList<>();

                String sql = SqlBuilder.buildSelect(getName(), filter, "r0.doc", -1, -1);

                try (
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    PreparedStatement delRec = conn.prepareStatement(sqlDeleteRec);
                    PreparedStatement delRef = conn.prepareStatement(sqlDeleteRef))
                {
                    while (rs.next())
                    {
                        HDict record = (HDict) JsonCodec.parse(rs.getString(1));
                        events.add(new DeleteEvent(this, record));

                        HRef id = record.id();
                        delRec.setString(1, id.toZinc());
                        delRec.execute();

                        delRef.setString(1, id.toZinc());
                        delRef.execute();
                    }
                }
 
                conn.commit();
                return events;
            }
            catch (SQLException e)
            {
                conn.rollback();
                throw e;
            }
            finally
            {
                conn.close();
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

////////////////////////////////////////////////////////////////
// private
////////////////////////////////////////////////////////////////

    /**
      * readRec
      */
    private HDict readRec(HRef id, PreparedStatement read) throws SQLException
    {
        read.setString(1, id.toZinc());
        try (ResultSet rs = read.executeQuery())
        {
            if (rs.next()) 
            {
                return (HDict) JsonCodec.parse(rs.getString(1));
            }
            else
            {
                throw new RuntimeException("A record with id '" + id + "' does not exist.");
            }
        }
    }

    /**
      * insertRec
      */
    private void insertRec(HDict record, PreparedStatement insRec)
    throws SQLException
    {
        String id = record.id().toZinc();

        String dis = record.dis();

        insRec.setString(1, id);
        insRec.setString(2, dis(record));
        insRec.setObject(3, JsonCodec.encode(record, false), Types.OTHER);
        insRec.execute();
    }

    /**
      * insertRefs
      */
    private void insertRefs(HDict record, PreparedStatement insRef)
    throws SQLException
    {
        String id = record.id().toZinc();

        Iterator it = record.iterator();
        while (it.hasNext())
        {
            Map.Entry entry = (Map.Entry) it.next();
            if (!entry.getKey().equals("id") && entry.getValue() instanceof HRef)
            {
                insRef.setString(1, (String) entry.getKey()); 
                insRef.setString(2, id);
                insRef.setString(3, ((HRef) entry.getValue()).toZinc());
                insRef.execute();
            }
        }
    }

    private static String dis(HDict record)
    {
        HVal v = record.get("dis", false); 
        if ((v != null) && (v instanceof HStr)) 
            return ((HStr) v).val;
        return "";
    }

    HDict computeDis(HDict record)
    {
//System.out.println("computeDis: aaa " + JsonCodec.encode(record));
        HDictBuilder hdb = null;

        // compute dis for id
        String dis = dis(record);

        if (dis != "")
        {
            hdb = new HDictBuilder().add(record);
            hdb.add("id", HRef.make(record.id().val, dis));
        }

        Iterator it = record.iterator();
        while (it.hasNext())
        {
            Map.Entry entry = (Map.Entry) it.next();
            if (!entry.getKey().equals("id") && entry.getValue() instanceof HRef)
            {
                if (hdb == null)
                    hdb = new HDictBuilder().add(record);
                String tag = (String) entry.getKey();
                HRef ref = (HRef) entry.getValue();

                try
                {
                    dis = disCache.get(ref);
                    hdb.add(tag, HRef.make(ref.val, dis));
                }
                catch (Exception e) { }
            }
        }

        // if nothing changed, return original record
        if (hdb != null)
            record = hdb.toDict();

//System.out.println("computeDis: bbb " + JsonCodec.encode(record));
        return record;
    }

    private class DisLoader extends CacheLoader<HRef,String>
    {
        public String load(HRef id)
        {
            try
            {
                try (Connection conn = db.getConnection())
                {
                    try (PreparedStatement prep = conn.prepareStatement(sqlSelectDis)) 
                    {
                        prep.setString(1, id.toZinc());
                        try (ResultSet rs = prep.executeQuery()) {
                            if (!rs.next())
                                throw new DisLookupException(getName(), id);
                            return rs.getString(1);
                        }
                    }
                }
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

////////////////////////////////////////////////////////////////
// attribs
////////////////////////////////////////////////////////////////

    private static final Logger LOG = LoggerFactory.getLogger(PostgresTable.class);

    private static final String[] DDL_RECS = new String[]  {
        "create table $recs (id text primary key, dis text, doc jsonb);",
        "create index on $recs using gin (doc);"
    };

    private static final String[] DDL_REFS = new String[]  {
        "create table $refs (tag text, fromId text, toRef text, primary key(tag, fromId, toRef));",
        "create index on $refs (tag);",
        "create index on $refs (fromId);",
        "create index on $refs (toRef);",
    };

    private static final String SELECT_SIZE = "select count(*) from $recs;";
    private static final String SELECT_REC  = "select doc from $recs where id = ?;";
    private static final String SELECT_DIS  = "select dis from $recs where id = ?;";
    private static final String INSERT_REC  = "insert into $recs (id, dis, doc) values (?, ?, ?);";
    private static final String INSERT_REF  = "insert into $refs (tag, fromId, toRef) values (?, ?, ?);";
    private static final String UPDATE_REC  = "update $recs set doc = ?, dis = ? where id = ?;";
    private static final String DELETE_REC  = "delete from $recs where id = ?;";
    private static final String DELETE_REF  = "delete from $refs where fromId = ?;";

    private final String[] ddlRecs;
    private final String[] ddlRefs;

    private final String sqlSelectSize;
    private final String sqlSelectRec;
    private final String sqlSelectDis;
    private final String sqlInsertRec;
    private final String sqlInsertRef;
    private final String sqlUpdateRec;
    private final String sqlDeleteRec;
    private final String sqlDeleteRef;

    private final LoadingCache<HRef,String> disCache;
    private static final int DIS_CACHE_SIZE = 100 * 1000;

    private final PostgresDatabase db;
}
