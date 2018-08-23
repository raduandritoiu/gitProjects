package treehouse.db;

import java.util.*;

import org.projecthaystack.*;

/**
  * A Archive is a storage area for time-series data that is associate with a 
  * particular set of HRef ids.
  */
public abstract class History
{
    protected History(Database db, String name)
    {
        assert(db != null);
        assert(name != null);

        this.db = db;
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
      * Read history time-series data for given identifier and time range. The
      * items returned are exclusive of start time and inclusive of end time.
      */
    public final HisCursor hisRead(HRef id, HDateTimeRange range) throws Exception
    {
        return doHisRead(id, range);
    }

    /**
      * Write a set of history time-series data for the given identifier.
      * If duplicate or out-of-order items are inserted then they 
      * will be gracefully merged.
      */
    public final void hisWrite(HRef id, Iterator<HisItem> items) throws Exception
    {
        doHisWrite(id, items);
    }

    /**
      * Implementation hook for hisRead().
      */
    protected abstract HisCursor doHisRead(HRef id, HDateTimeRange range) throws Exception;

    /**
      * Implementation hook for hisWrite().
      */
    protected abstract void doHisWrite(HRef id, Iterator<HisItem> items) throws Exception;

////////////////////////////////////////////////////////////////
// access
////////////////////////////////////////////////////////////////

    /** Return the Database. */
    public final Database getDatabase() { return db; }

    /** Return the name of this History. */
    public final String getName() { return name; }

////////////////////////////////////////////////////////////////
// attribs
////////////////////////////////////////////////////////////////

    private final Database db;
    private final String name;
}
