package treehouse.haystack;

import java.util.*;

import com.google.common.collect.AbstractIterator;
import org.projecthaystack.*;

/**
  * Retain only those items from the Iterator which match the Filter
  */
public class FilterIterator extends AbstractIterator<HDict>
{
    public FilterIterator(
        Iterator<HDict> itr,
        Filter filter, 
        Filter.Pather pather)
    {
        this.itr = itr;
        this.filter = filter;
        this.pather = pather;
    }

    protected HDict computeNext()
    {
        while (itr.hasNext())
        {
            HDict row = itr.next();
            if (filter.include(row, pather))
                return row;
        }
        return endOfData();
    }

    private final Iterator<HDict> itr;
    private final Filter filter;
    private final Filter.Pather pather;
}
