package treehouse.db;

import java.util.*;

import com.google.common.util.concurrent.Service;
import com.google.common.util.concurrent.Service.State;
import org.projecthaystack.*;

import treehouse.haystack.*;

/**
  * An Archive is a storage area for time-series data that is associated
  * with a parent table.
  */
public abstract class Archive
{
    protected Archive(Database db, Table parent, String name)
    {
        assert(db != null);
        assert(parent != null);
        assert(name != null);

        this.db = db;
        this.parent = parent;
        this.name = name;
    }

    final void open()  { doOpen();  }
    final void close() { doClose(); }

    /**
      * Implementation hook for open().
      */
    protected abstract void doOpen();

    /**
      * Implementation hook for close().
      */
    protected abstract void doClose();

    /**
      * Read history time-series data for given filter and time range. The
      * items returned are exclusive of start time and inclusive of end time.
      * You must specify either a range, or a filter, or both.
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
    public final HisCursor archiveRead(HDateTimeRange range, Filter filter)
    {
        return archiveRead(range, filter, -1, -1);
    }

    /**
      * Read history time-series data for given filter and time range. The
      * items returned are exclusive of start time and inclusive of end time.
      * You must specify either a range, or a filter, or both.
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
    public final HisCursor archiveRead(
        HDateTimeRange range, Filter filter,
        int skip, int limit)
    {
        assert(db.isRunning());
        return doArchiveRead(range, filter, skip, limit);
    }

    /**
      * Write a set of history time-series data. If out-of-order
      * items are inserted then they will be gracefully merged.
      */
    public final void archiveWrite(Iterator<HisItem> items)
    {
        assert(db.isRunning());
        doArchiveWrite(items);
    }

    /**
      * Implementation hook for archiveRead().
      */
    protected abstract HisCursor doArchiveRead(
        HDateTimeRange range, Filter filter,
        int skip, int limit);

    /**
      * Implementation hook for archiveWrite().
      */
    protected abstract void doArchiveWrite(Iterator<HisItem> items);

////////////////////////////////////////////////////////////////
// access
////////////////////////////////////////////////////////////////

    /** Return the Database. */
    public final Database getDatabase() { return db; }

    /** Return the parent table. */
    public final Table getParent() { return parent; }

    /** Return the name of this Archive. */
    public final String getName() { return name; }

////////////////////////////////////////////////////////////////
// attribs
////////////////////////////////////////////////////////////////

    private final Database db;
    private final Table parent;
    private final String name;
}
