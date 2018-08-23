package treehouse.db.file;

import java.util.*;

import org.projecthaystack.*;

import com.google.common.collect.ComparisonChain;

/**
  * A Chunk is an immutable object which represents all the 
  * time-series data for a given ID, for a given UTC year-and-month.
  *
  * All of the data for a given Chunk resides in one file.
  */
class Chunk implements Comparable<Chunk>
{
    /**
      * Create a new Chunk
      */
    static Chunk make(HRef id, HDateTime ts)
    {
        return new Chunk(id, ts);
    }

    private Chunk(HRef id, HDateTime ts)
    {
        // ensure UTC
        if (!ts.tz.name.equals(HTimeZone.UTC.name))
            ts = HDateTime.make(ts.millis(), HTimeZone.UTC);

        this.id = id;
        this.year = ts.date.year;
        this.month = ts.date.month;
    }

    @Override
    public String toString()
    {
        return getFilePath();
    }

    @Override
    public boolean equals(Object obj)
    {
        return (obj instanceof Chunk) ?
            compareTo((Chunk) obj) == 0 : 
            false;
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(id.val, year, month);
    }

    @Override
    public int compareTo(Chunk that)
    {
        return ComparisonChain.start()
            .compare(id.val, that.id.val)
            .compare(year, that.year)
            .compare(month, that.month)
            .result();
    }

    /**
      * Return the path to the file which stores all of this chunk's data.
      */
    String getFilePath()
    {
        return directory() + "/" + file();
    }

////////////////////////////////////////////////////////////////
// package-scope
////////////////////////////////////////////////////////////////

    /**
      * return bucket/id
      */
    String directory()
    {
        int n = Math.abs(id.val.hashCode() % 65536);
        String bucket = Integer.toString(n, 16);
        while (bucket.length() < 4) 
            bucket = "0" + bucket;

        if (bucket.length() != 4)
            throw new IllegalStateException();

        return 
            bucket.substring(0,2) + "/" + 
            bucket.substring(2,4) + "/" + 
            id.val;
    }

    /** Encode as "YYYY_MM.his" */
    String file()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(year).append('_');
        if (month < 10) sb.append('0'); 
        sb.append(month).append(".his");
        return sb.toString();
    }

////////////////////////////////////////////////////////////////
// attributes
////////////////////////////////////////////////////////////////

    final HRef id;

    /** Four digit year such as 2011 */
    final int year;

    /** Month as 1-12 (Jan is 1, Dec is 12) */
    final int month;
}
