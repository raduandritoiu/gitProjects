package treehouse.db.jdbc.sqlite;

import java.sql.*;
import java.util.*;
import java.util.concurrent.locks.*;

import org.projecthaystack.*;

import treehouse.db.*;
import treehouse.haystack.*;
import treehouse.haystack.io.*;

import com.google.common.collect.Iterables; 

class SqliteCursor implements Cursor 
{
    SqliteCursor(
        final SqliteTable table,
        final Filter filter, 
        int skip, int limit)
    {
        this.table = table;

        table.lock.readLock().lock();

        Iterable<HDict> ibl = new Iterable() {
            public Iterator iterator() { 
                return new FilterIterator(
                    table.primaryKey.values().iterator(), 
                    filter, table.pather);
        }};
        if (skip  >  0) ibl = Iterables.skip  (ibl,  skip);
        if (limit >= 0) ibl = Iterables.limit (ibl, limit);

        this.itr = ibl.iterator();
    }

    public boolean hasNext() { return itr.hasNext(); }

    public HDict next() { return itr.next(); }

    public void remove() { throw new UnsupportedOperationException(); }

    // DON'T FORGET TO CALL THIS!
    public void close()
    {
        table.lock.readLock().unlock();
    }

    private final SqliteTable table;
    private final Iterator<HDict> itr;
}
