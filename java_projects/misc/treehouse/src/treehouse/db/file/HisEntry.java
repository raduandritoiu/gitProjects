package treehouse.db.file;

import org.projecthaystack.*;

import treehouse.haystack.*;

class HisEntry
{
    HisEntry(long millis, HVal obj)
    {
        this.millis = millis;
        this.obj = obj;
    }

    public final long millis;
    public final HVal obj;
}

