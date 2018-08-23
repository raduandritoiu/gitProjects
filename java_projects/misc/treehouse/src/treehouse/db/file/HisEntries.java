package treehouse.db.file;

import java.util.*;

import org.projecthaystack.*;

import treehouse.db.*;

/**
  * HisEntries keeps a sequence of HisEntry values, sorted by millis.
  * <p>
  * There is at most one entry in the sequence per distinct millis value.
  */
class HisEntries
{
    void put(HisEntry entry)
    {
        map.put(entry.millis, entry);
    }

    void putAll(Iterator<HisEntry> itr)
    {
        while (itr.hasNext())
        {
            HisEntry entry = itr.next();
            map.put(entry.millis, entry);
        }
    }

    Iterator<HisEntry> entries()
    {
        return map.values().iterator();
    }

    private TreeMap<Long,HisEntry> map = new TreeMap<>();
}
