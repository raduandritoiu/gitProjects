package treehouse.db;

import java.util.*;

import com.google.common.collect.ImmutableSet;
import org.projecthaystack.*;

import treehouse.haystack.*;
import treehouse.haystack.io.*;

/**
  * A Diff represent the act of transforming an HDict from one state to another.
  */
public class Diff
{
    /**
      * Make a Diff.  You must specify either 'put', or 'remove', or both.
      * You cannot include 'id' as a tag in either 'put' or 'remove'.
      */
    public static Diff make(HDict put, ImmutableSet<String> remove)
    {
        if ((put == null) && (remove == null))
            throw new IllegalStateException(
                "Invalid diff: must specify 'put' or 'remove'");

        checkForbiddenKey("id", put, remove);

        return new Diff(put, remove);
    }

    private Diff(HDict put, ImmutableSet<String> remove)
    {
        this.put = put;
        this.remove = remove;
    }

    private static void checkForbiddenKey(
        String key, 
        HDict put, 
        ImmutableSet<String> remove)
    {
        if (put != null && put.has(key))
            throw new IllegalStateException(
                "Invalid diff: cannot contain '" + key + "'");

        if (remove != null && remove.contains(key))
            throw new IllegalStateException(
                "Invalid diff: cannot contain '" + key + "'");
    }

    public String toString()
    {
        return JsonCodec.encode(toJson());
    }

    public HDict toJson()
    {
        HDictBuilder db = new HDictBuilder();

        if (put != null)
        {
            db.add("put", put);
        }

        if (remove != null)
        {
            HListBuilder lb = new HListBuilder();
            for (String r : remove)
                lb.add(HStr.make(r));
            db.add("remove", lb.toList());
        }

        return db.toDict();
    }

    /**
      * Create a new HDict that is the result of applying this Diff 
      * to the given HDict.
      */
    public HDict apply(HDict dict)
    {
        HDictBuilder b = new HDictBuilder().add(dict);

        if (put != null) b.add(put);

        if (remove != null) 
            for (String r : remove)
                b.remove(r);

        return b.toDict();
    }

    public final HDict put;
    public final ImmutableSet<String> remove;
}
