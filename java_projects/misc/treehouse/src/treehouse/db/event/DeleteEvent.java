package treehouse.db.event;

import org.projecthaystack.*;

import treehouse.db.*;
import treehouse.haystack.*;
import treehouse.event.*;

/**
  * An DeleteEvent represents a delete on a Table.
  */
public class DeleteEvent extends TableEvent
{
    public DeleteEvent(Table table, HDict record)
    { 
        super(table, record); 
    }

    public HDict toJson()
    {
        return new HDictBuilder()
            .add("eventType", HStr.make("delete"))
            .add("table", HStr.make(table.getName()))
            .add("record", record)
            .toDict();
    }

    public Type getType() { return Type.DELETE; }
}
