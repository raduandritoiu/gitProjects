package treehouse.db;

import org.projecthaystack.*;

import treehouse.haystack.*;

/**
  * A single History Item
  */
public class HisItem
{
    public HisItem(HDateTime ts, HVal obj)
    {
        assert(ts != null);
        this.ts = ts;
        this.obj = obj;
    }

    public final HDateTime ts;
    public final HVal obj;
}

