package treehouse.db.event;

import org.projecthaystack.*;

import treehouse.db.*;
import treehouse.event.*;
import treehouse.haystack.*;
import treehouse.haystack.io.*;

/**
  * A TableEvent represents an insert, update, or delete on a Table.
  */
public abstract class TableEvent implements Event
{
    public TableEvent(Table table, HDict record) 
    { 
        this.table = table;
        this.record = record;
    }

    public final String toString() 
    { 
        return JsonCodec.encode(toJson()); 
    }

    public abstract HDict toJson();

    public abstract Type getType();

    public enum Type { INSERT, UPDATE, DELETE }

    /** The Table that the event happened on.  */
    public final Table table;

    /** The record that was inserted, updated, or deleted.  */
    public final HDict record;
}
