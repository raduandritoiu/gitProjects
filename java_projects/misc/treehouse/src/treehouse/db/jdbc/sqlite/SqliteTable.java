package treehouse.db.jdbc.sqlite;

import java.sql.*;
import java.util.*;
import java.util.concurrent.locks.*;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import org.projecthaystack.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import treehouse.db.*;
import treehouse.db.event.*;
import treehouse.db.jdbc.*;
import treehouse.haystack.*;
import treehouse.haystack.io.*;

/**
  * SqliteTable is a Table that is persisted into a Sqlite Database.
  */
class SqliteTable extends Table
{
    SqliteTable(SqliteDatabase db, String name)
    {
        super(db, name);
        this.db = db;

        this.ddlRecs = new String[DDL_RECS.length];
        for (int i = 0; i < DDL_RECS.length; i++) 
            ddlRecs[i] = DDL_RECS[i].replace("$", name);

        sqlInsertRec = INSERT_REC.replace("$", name);
        sqlUpdateRec = UPDATE_REC.replace("$", name);
        sqlDeleteRec = DELETE_REC.replace("$", name);
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
                JdbcDatabase.ensureTable(conn, getName(), ddlRecs);
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
        // no need to lock here, since this method is only called while NEW
        primaryKey.put(record.id(), record);
    }

////////////////////////////////////////////////////////////////
// Querying
////////////////////////////////////////////////////////////////

    /**
      * Implementation hook for readCount().
      */
    protected int doReadSize()
    {
        lock.readLock().lock();
        try
        {
            return primaryKey.size();
        }
        finally
        {
            lock.readLock().unlock();
        }
    }

    /**
      * Implementation hook for readById().
      */
    protected HDict doReadById(HRef id)
    {
        LOG.trace("readById " + id);

        lock.readLock().lock();
        try
        {
            return readRec(id);
        }
        finally
        {
            lock.readLock().unlock();
        }
    }

    /**
      * Implementation hook for read().
      */
    protected HDict doRead(Filter filter)
    {
        try
        {
            try (Cursor cursor = doReadAll(filter, -1, -1))
            {
                if (cursor.hasNext())
                    return cursor.next();
                else
                    throw new RuntimeException("There is no record matching filter '" + filter + "'.");
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
      * Implementation hook for readAll().
      */
    protected Cursor doReadAll(Filter filter, int skip, int limit)
    {
        return new SqliteCursor(this, filter, skip, limit);
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

        lock.writeLock().lock();
        try
        {
            Connection conn = db.getConnection();
            try
            {
                conn.setAutoCommit(false);

                try (PreparedStatement insRec = conn.prepareStatement(sqlInsertRec))
                {
                    String id = record.id().toZinc();
                    insRec.setString(1, id);
                    insRec.setString(2, JsonCodec.encode(record));
                    insRec.execute();
                }

                conn.commit();

                primaryKey.put(record.id(), record);
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
        finally
        {
            lock.writeLock().unlock();
        }
    }

    /**
      * Implementation hook for batchInsert().
      */
    protected List<InsertEvent> doBatchInsert(Iterator<HDict> records)
    {
        LOG.trace("batchInsert");

        lock.writeLock().lock();
        try
        {
            Connection conn = db.getConnection();
            try
            {
                conn.setAutoCommit(false);
                List<InsertEvent> events = new ArrayList<>();

                try (PreparedStatement insRec = conn.prepareStatement(sqlInsertRec))
                {
                    while (records.hasNext())
                    {
                        HDict record = records.next();

                        String id = record.id().toZinc();
                        insRec.setString(1, id);
                        insRec.setString(2, JsonCodec.encode(record));
                        insRec.execute();

                        events.add(new InsertEvent(this, record));
                    }
                }

                conn.commit();

                for (InsertEvent e : events)
                    primaryKey.put(e.record.id(), e.record);
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
        finally
        {
            lock.writeLock().unlock();
        }
    }

    /**
      * Implementation hook for updateById().
      */
    protected UpdateEvent doUpdateById(HRef id, Diff diff)
    {
        LOG.trace("updateById " + id + ", " + diff);

        lock.writeLock().lock();
        try
        {
            Connection conn = db.getConnection();
            try
            {
                conn.setAutoCommit(false);
                UpdateEvent event;

                try (PreparedStatement updRec = conn.prepareStatement(sqlUpdateRec))
                {
                    HDict oldRec = readRec(id);
                    HDict newRec = diff.apply(oldRec);
                    event = new UpdateEvent(this, newRec, diff, oldRec);

                    updRec.setString(1, JsonCodec.encode(newRec));
                    updRec.setString(2, id.toZinc());
                    updRec.execute();
                }

                conn.commit();

                primaryKey.put(event.record.id(), event.record);
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
        finally
        {
            lock.writeLock().unlock();
        }
    }

    /**
      * Implementation hook for updateAll().
      */
    protected List<UpdateEvent> doUpdateAll(Filter filter, Diff diff)
    {
        LOG.trace("updateAll " + filter + ", " + diff);

        lock.writeLock().lock();
        try
        {
            Connection conn = db.getConnection();
            try
            {
                conn.setAutoCommit(false);
                List<UpdateEvent> events = new ArrayList<>();

                FilterIterator itr = new FilterIterator(
                    primaryKey.values().iterator(), filter, pather);

                try (PreparedStatement updRec = conn.prepareStatement(sqlUpdateRec))
                {
                    while (itr.hasNext())
                    {
                        HDict oldRec = itr.next();
                        HRef id = oldRec.id();
                        HDict newRec = diff.apply(oldRec);

                        events.add(new UpdateEvent(this, newRec, diff, oldRec));

                        updRec.setString(1, JsonCodec.encode(newRec));
                        updRec.setString(2, id.toZinc());
                        updRec.execute();
                    }
                }
 
                conn.commit();

                for (UpdateEvent e : events)
                    primaryKey.put(e.record.id(), e.record);
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
        finally
        {
            lock.writeLock().unlock();
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

                try (PreparedStatement delRec = conn.prepareStatement(sqlDeleteRec))
                {
                    HDict record = readRec(id);
                    event = new DeleteEvent(this, record);

                    delRec.setString(1, id.toZinc());
                    delRec.execute();
                }

                conn.commit();

                primaryKey.remove(id);
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

                FilterIterator itr = new FilterIterator(
                    primaryKey.values().iterator(), filter, pather);

                try (PreparedStatement delRec = conn.prepareStatement(sqlDeleteRec))
                {
                    while (itr.hasNext())
                    {
                        HDict record = itr.next();
                        events.add(new DeleteEvent(this, record));

                        HRef id = record.id();
                        delRec.setString(1, id.toZinc());
                        delRec.execute();
                    }
                }
 
                conn.commit();

                for (DeleteEvent e : events)
                    primaryKey.remove(e.record.id());
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

    private HDict readRec(HRef id)
    {
        HDict rec = primaryKey.get(id);
        if (rec == null)
            throw new NoSuchElementException(
                "There is no record with id '" + id + "'.");
        return rec;
    }

////////////////////////////////////////////////////////////////
// attribs
////////////////////////////////////////////////////////////////

    private static final Logger LOG = LoggerFactory.getLogger(SqliteTable.class);

    private static final String[] DDL_RECS = new String[]  {
        "create table $ (id text primary key, doc text);",
    };

    private static final String INSERT_REC = "insert into $ (id, doc) values (?, ?);";
    private static final String UPDATE_REC = "update $ set doc = ? where id = ?;";
    private static final String DELETE_REC = "delete from $ where id = ?;";

    private final String[] ddlRecs;

    private final String sqlInsertRec;
    private final String sqlUpdateRec;
    private final String sqlDeleteRec;

    private final SqliteDatabase db;

    final ReadWriteLock lock = new ReentrantReadWriteLock();
    final Map<HRef,HDict> primaryKey = new HashMap<>();

    final Filter.Pather pather = new Filter.Pather() {
      public HDict find(HRef id) {
          return primaryKey.get(id);
    }};
}
