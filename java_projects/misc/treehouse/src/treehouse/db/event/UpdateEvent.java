package treehouse.db.event;

import org.projecthaystack.*;

import treehouse.db.*;
import treehouse.haystack.*;
import treehouse.event.*;

/**
  * An UpdateEvent represents an update on a Table.
  */
public class UpdateEvent extends TableEvent
{
    public UpdateEvent(Table table, HDict record, Diff diff, HDict oldRecord) 
    { 
        super(table, record); 
        this.diff = diff;
        this.oldRecord = oldRecord;
    }

    public HDict toJson()
    {
        return new HDictBuilder()
            .add("eventType", HStr.make("update"))
            .add("table", HStr.make(table.getName()))
            .add("record", record)
            .add("oldRecord", oldRecord)
            .add("diff", diff.toJson())
            .toDict();
    }

    public Type getType() { return Type.UPDATE; }

    /** The diff that was used to update the record. */
    public final Diff diff;

    /** The record's state before it was updated.  */
    public final HDict oldRecord;
}
