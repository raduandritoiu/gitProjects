package treehouse.db.event;

import org.projecthaystack.*;

import treehouse.db.*;
import treehouse.haystack.*;
import treehouse.event.*;

/**
  * A InsertEvent represents an insert on a Table.
  */
public class InsertEvent extends TableEvent
{
    public InsertEvent(Table table, HDict record) 
    { 
        super(table, record); 
    }

    public HDict toJson()
    {
        return new HDictBuilder()
            .add("eventType", HStr.make("insert"))
            .add("table", HStr.make(table.getName()))
            .add("record", record)
            .toDict();
    }

    public Type getType() { return Type.INSERT; }
}
