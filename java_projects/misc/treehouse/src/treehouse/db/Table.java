package treehouse.db;

import java.util.*;

import com.google.common.collect.ConcurrentHashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.util.concurrent.Service;
import com.google.common.util.concurrent.Service.State;
import org.projecthaystack.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import treehouse.db.event.*;
import treehouse.event.*;
import treehouse.haystack.*;
import treehouse.haystack.io.*;
import treehouse.util.*;

/**
  * A Table is a collection of HDict records.
  */
public abstract class Table
{
    protected Table(Database db, String name)
    {
        assert(db != null);
        assert(name != null);

        this.db = db;
        this.name = name;
    }

    public String toString()
    {
        return "[Table " +
            "name:" + name + "]";
    }

    /**
      * Open the database.
      */
    final void open() 
    {
        try
        {
            long begin = System.currentTimeMillis();

            doOpen();
            scanOnOpen();

            long elapsed = System.currentTimeMillis() - begin;
            LOG.info("Opened " + getName() + " in " + elapsed + "ms.");
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
      * Close the database.
      */
    final void close() 
    {
        doClose();
    }

    /**
      * Implementation hook for open().
      */
    protected abstract void doOpen();

    /**
      * Implementation hook for close().
      */
    protected abstract void doClose();

    /**
      * Hook for processing a record that is being scanned during open().
      */
    protected abstract void openRecord(HDict record);

////////////////////////////////////////////////////////////////
// Querying
////////////////////////////////////////////////////////////////

    /**
      * Read the number of records in the table;
      */
    public final int readSize()
    {
        assert(db.isRunning());
        return doReadSize();
    }

    /**
      * Return the record having the given id from the table.  
      *
      * @throws Exception if the record does not exist.
      */
    public final HDict readById(HRef id)
    {
        assert(db.isRunning());
        return doReadById(id);
    }

    /**
      * Apply the given filter to all of the records in the table, and returning
      * the first matching record.
      *
      * @throws Exception if no such record exists.
      */
    public final HDict read(Filter filter)
    {
        assert(db.isRunning());
        return doRead(filter);
    }

    /**
      * Apply the given filter to all of the records in the table, returning
      * only those records which match the filter.
      * <p>
      * You <b><i>must</i></b> call close() on the cursor when you are done
      * with it. The use of a try-with-resources statement is encouraged.
      */
    public final Cursor readAll(Filter filter)
    {
        return readAll(filter, -1, -1);
    }

    /**
      * Apply the given filter to all of the records in the table, returning
      * only those records which match the filter.
      * <p>
      * If 'skip' is greater than zero, then that many records will be skipped
      * before any are returned.
      * <p>
      * If 'limit' is greater than zero, then a maximum of that many records
      * will be returned.
      * <p>
      * You <b><i>must</i></b> call close() on the cursor when you are done
      * with it. The use of a try-with-resources statement is encouraged.
      */
    public final Cursor readAll(Filter filter, int skip, int limit)
    {
        assert(db.isRunning());
        return doReadAll(filter, skip, limit);
    }

    /**
      * Return the name and count of each tag present in the table.
      *
      * @throws Exception if the record does not exist.
      */
    public final HList readTags()
    {
        assert(db.isRunning());

        List<HDict> list = new ArrayList();
        for (Multiset.Entry<String> e : tags.entrySet())
        {
            list.add(new HDictBuilder()
                .add("name", HStr.make(e.getElement()))
                .add("count", HNum.make(e.getCount()))
                .toDict());
        }

        Collections.sort(list, new Comparator<HDict>() {
            public int compare(HDict r1, HDict r2) {
                return r1.getStr("name").compareTo(r2.getStr("name"));
            }
        });

        HListBuilder lb = new HListBuilder();
        for (HDict dict : list) 
            lb.add(dict);
        return lb.toList();
    }

////////////////////////////////////////////////////////////////
// Insert, Update, Delete
////////////////////////////////////////////////////////////////

    /**
      * Insert the given record. The record musts have an id.
      *
      * @throws Exception if the record already exists.
      */
    public final void insert(HDict record)
    {
        assert(db.isRunning());

        InsertEvent event = doInsert(record);

        // update tags
        tags.addAll(Arrays.asList(event.record.tags()));

        // dispatch
        eventBus.dispatch(event);
    }

    /**
      * Insert the given records. The records must have an id.
      *
      * @throws Exception if any record already exists.
      */
    public final void batchInsert(Iterator<HDict> records)
    {
        assert(db.isRunning());

        List<InsertEvent> events = doBatchInsert(records);

        // update tags
        for (InsertEvent e : events) 
            tags.addAll(Arrays.asList(e.record.tags()));

        // dispatch
        for (InsertEvent e : events) 
            eventBus.dispatch(e);
    }

    /**
      * Apply the diff to the record having the given id.
      *
      * @throws Exception if the record does not exist.
      */
    public final void updateById(HRef id, Diff diff)
    {
        assert(db.isRunning());

        UpdateEvent event = doUpdateById(id, diff);

        // update tags
        for (String tag : event.oldRecord.tags())
            tags.remove(tag);
        tags.addAll(Arrays.asList(event.record.tags()));

        // dispatch
        eventBus.dispatch(event);
    }

    /**
      * Apply the diff to all records matching the filter. 
      */
    public final void updateAll(Filter filter, Diff diff)
    {
        assert(db.isRunning());

        List<UpdateEvent> events = doUpdateAll(filter, diff);

        // update tags
        for (UpdateEvent e : events)
        {
            for (String tag : e.oldRecord.tags())
                tags.remove(tag);
            tags.addAll(Arrays.asList(e.record.tags()));
        }

        // dispatch
        for (UpdateEvent e : events) 
            eventBus.dispatch(e);
    }

    /**
      * Delete the record.
      *
      * @throws Exception if the record does not exist.
      */
    public final void deleteById(HRef id)
    {
        assert(db.isRunning());

        DeleteEvent event = doDeleteById(id);

        // update tags
        for (String tag : event.record.tags())
            tags.remove(tag);

        // dispatch
        eventBus.dispatch(event);
    }

    /**
      * Delete all the records matching the filter.
      */
    public final void deleteAll(Filter filter)
    {
        assert(db.isRunning());

        List<DeleteEvent> events = doDeleteAll(filter);

        // update tags
        for (DeleteEvent e : events)
        {
            for (String tag : e.record.tags())
                tags.remove(tag);
        }

        // dispatch
        for (DeleteEvent e : events) 
            eventBus.dispatch(e);
    }

////////////////////////////////////////////////////////////////
// Implementation hooks
////////////////////////////////////////////////////////////////

    /**
      * Implementation hook for readSize().
      */
    protected abstract int doReadSize();

    /**
      * Implementation hook for readById().
      */
    protected abstract HDict doReadById(HRef id);

    /**
      * Implementation hook for read().
      */
    protected abstract HDict doRead(Filter filter);

    /**
      * Implementation hook for readAll().
      */
    protected abstract Cursor doReadAll(Filter filter, int skip, int limit);

    /**
      * Implementation hook for insert().
      */
    protected abstract InsertEvent doInsert(HDict record);

    /**
      * Implementation hook for batchInsert().
      */
    protected abstract List<InsertEvent> doBatchInsert(Iterator<HDict> records);

    /**
      * Implementation hook for updateById().
      */
    protected abstract UpdateEvent doUpdateById(HRef id, Diff diff);

    /**
      * Implementation hook for updateAll().
      */
    protected abstract List<UpdateEvent> doUpdateAll(Filter filter, Diff diff);

    /**
      * Implementation hook for deleteById().
      */
    protected abstract DeleteEvent doDeleteById(HRef id);

    /**
      * Implementation hook for deleteAll().
      */
    protected abstract List<DeleteEvent> doDeleteAll(Filter filter);

////////////////////////////////////////////////////////////////
// Listeners
////////////////////////////////////////////////////////////////

    /**
      * Registers the Listener so that it will recieve a TableEvent
      * when a record is inserted, update, or deleted.
      */
    public final void register(Listener<TableEvent> listener)
    {
        assert(db.isRunning());
        eventBus.register(listener);
    }

    /**
      * Unregisters the Listener so that it will no longer recieve TableEvents.
      */
    public final void unregister(Listener<TableEvent> listener)
    {
        assert(db.isRunning());
        eventBus.unregister(listener);
    }

////////////////////////////////////////////////////////////////
// private
////////////////////////////////////////////////////////////////

    private void scanOnOpen() throws Exception
    {
        // count the number of records
        int size = doReadSize();

        // populate the 'tags' multiset
        Filter filter = FilterParser.parse("id");
        AsciiProgressBar prog = new AsciiProgressBar("Init " + getName(), size);
        try (Cursor cursor = doReadAll(filter, -1, -1))
        {
            int num = 0;
            while (cursor.hasNext())
            {
                if ((++num % 100) == 0) prog.show(num);
                HDict record = cursor.next();
                tags.addAll(Arrays.asList(record.tags()));

                // give subclasses a chance to do something with the record
                openRecord(record);
            }
        }
        prog.show(size);
        System.out.println();
    }

////////////////////////////////////////////////////////////////
// access
////////////////////////////////////////////////////////////////

    /** Return the Database. */
    public final Database getDatabase() { return db; }

    /** Return the name of this table. */
    public final String getName() { return name; }

////////////////////////////////////////////////////////////////
// attribs
////////////////////////////////////////////////////////////////

    private static final Logger LOG = LoggerFactory.getLogger(Table.class);

    private final Database db;
    private final String name;

    private EventBus<TableEvent> eventBus = new EventBus<>();

    private final ConcurrentHashMultiset<String> tags = 
        ConcurrentHashMultiset.<String>create();
}
