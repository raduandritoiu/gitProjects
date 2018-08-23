package treehouse.db.jdbc.postgres;

import java.sql.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.projecthaystack.*;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import treehouse.db.jdbc.*;
import treehouse.haystack.*;
import treehouse.haystack.io.*;
import treehouse.db.*;

/**
  * A Archive is a storage area for time-series data.
  */
class PostgresArchive extends Archive
{
    PostgresArchive(PostgresDatabase db, PostgresTable parent, String name)
    {
        super(db, parent, name);

        this.db = db;
        this.parent = parent;
        this.timeZones = new TimeZones(db);

        this.ddlRecs = new String[DDL_RECS.length];
        this.ddlRefs = new String[DDL_REFS.length];

        for (int i = 0; i < DDL_RECS.length; i++) ddlRecs[i] = DDL_RECS[i].replace("$", name + "_");
        for (int i = 0; i < DDL_REFS.length; i++) ddlRefs[i] = DDL_REFS[i].replace("$", name + "_");

        sqlInsert    = INSERT      .replace("$", name + "_");
        sqlInsertRef = INSERT_REF .replace("$", name + "_");
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
                JdbcDatabase.ensureTable(conn, getName() + "_arch",     ddlRecs);
                JdbcDatabase.ensureTable(conn, getName() + "_archrefs", ddlRefs);
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
      * Implementation hook for archiveRead().
      */
    protected HisCursor doArchiveRead(
        HDateTimeRange range, Filter filter,
        int skip, int limit)
    {
        if ((range == null) && (filter == null))
            throw new IllegalStateException(
                "Invalid archiveRead: must specify range or filter");

        try
        {
            long begin = System.currentTimeMillis();

            String sql = SqlBuilder.buildArchiveSelect(
                getName(), getParent().getName(), 
                range, filter, skip, limit);

            LOG.info("archiveRead begin: " + 
                range + ", " + filter + ", " + skip + ", " + limit + ", " + 
                sql.replace('\n', ' '));

            Connection conn = db.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            long end = System.currentTimeMillis();
            long elapsed = end - begin;
            LOG.info("archiveRead end: " + elapsed + "ms for " + range + ", " + filter);

            return new PostgresArchiveCursor(conn, stmt, rs, range, timeZones);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
      * Implementation hook for archiveWrite().
      */
    protected void doArchiveWrite(Iterator<HisItem> items)
    {
        LOG.trace("archiveWrite");

        try
        {
            Connection conn = db.getConnection();
            try
            {
                conn.setAutoCommit(false);

                try (PreparedStatement insArchive = conn.prepareStatement(
                        sqlInsert, Statement.RETURN_GENERATED_KEYS); 
                     PreparedStatement insArchiveRef = conn.prepareStatement(sqlInsertRef))
                {
                    while (items.hasNext())
                    {
                        HisItem item = items.next();

                        long millis = item.ts.millis();
                        int chunk = chunk(millis);
                        int offset = offset(millis);

                        String tz = item.ts.tz.name;
                        String doc = JsonCodec.encode(item.obj);

                        insArchive.setInt(1, chunk);
                        insArchive.setInt(2, offset);
                        insArchive.setInt(3, timeZones.getId(tz));
                        insArchive.setObject(4, doc, Types.OTHER);
                        insArchive.executeUpdate();

                        // create refs, but only if there is a parent table to join into
                        if ((parent != null) && (item.obj instanceof HDict))
                        {
                            // fetch the rowid
                            long rowid = -1;
                            try (ResultSet keys = insArchive.getGeneratedKeys()) 
                            {
                                keys.next();
                                rowid = keys.getLong(1);
                            }

                            // insert refs
                            insertArchiveRefs(rowid, ((HDict) item.obj), insArchiveRef);
                        }
                    }
                }
 
                conn.commit();
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
// package
////////////////////////////////////////////////////////////////

    static int chunk(long millis)
    {
        return (int) (millis / MILLIS_PER_CHUNK);
    }

    static int offset(long millis)
    {
        return (int) (millis % MILLIS_PER_CHUNK);
    }

////////////////////////////////////////////////////////////////
// private
////////////////////////////////////////////////////////////////

    /**
      * insertArchiveRefs
      */
    private void insertArchiveRefs(long rowid, HDict record, PreparedStatement insArchiveRef)
    throws SQLException
    {
        Iterator it = record.iterator();
        while (it.hasNext())
        {
            Map.Entry entry = (Map.Entry) it.next();
            if (entry.getValue() instanceof HRef)
            {
                insArchiveRef.setString(1, (String) entry.getKey()); 
                insArchiveRef.setLong(2, rowid);
                insArchiveRef.setString(3, ((HRef) entry.getValue()).toZinc());
                insArchiveRef.execute();
            }
        }
    }

////////////////////////////////////////////////////////////////
// attribs
////////////////////////////////////////////////////////////////

    private static final Logger LOG = LoggerFactory.getLogger(PostgresArchive.class);

    // A 'chunk' is slightly larger than the number of milliseconds in a day.
    // We use a nice round number to make it easier to debug if need be.
    static final long MILLIS_PER_CHUNK = 100 * 1000 * 1000;

    private static final String[] DDL_RECS = new String[]  {
        "create table $arch (rowid serial primary key, tsChunk integer, tsOffset integer, tz integer references timezones(id), doc jsonb);",
        "create index on $arch (tsChunk);",
        "create index on $arch using gin (doc);"
    };

    private static final String[] DDL_REFS = new String[]  {
        "create table $archrefs (tag text, fromId integer, toRef text, primary key (tag, fromId, toRef));",
        "create index on $archrefs (tag);",
        "create index on $archrefs (fromId);",
        "create index on $archrefs (toRef);"
    };

    private static final String INSERT     = "insert into $arch (tsChunk, tsOffset, tz, doc) values (?, ?, ?, ?);";
    private static final String INSERT_REF = "insert into $archrefs (tag, fromId, toRef) values (?, ?, ?);";

    private final String[] ddlRecs;
    private final String[] ddlRefs;

    private final String sqlInsert;
    private final String sqlInsertRef;

    private final PostgresDatabase db;
    private final PostgresTable parent;
    private final TimeZones timeZones;
}
