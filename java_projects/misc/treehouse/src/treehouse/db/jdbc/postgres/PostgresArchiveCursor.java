package treehouse.db.jdbc.postgres;

import java.sql.*;
import java.util.*;

import com.google.common.collect.AbstractIterator;
import org.projecthaystack.*;

import treehouse.db.jdbc.*;
import treehouse.haystack.*;
import treehouse.haystack.io.*;
import treehouse.db.*;

class PostgresArchiveCursor
    extends AbstractIterator<HisItem>
    implements HisCursor
{
    PostgresArchiveCursor(
        Connection conn, 
        Statement stmt, 
        ResultSet rs,
        HDateTimeRange range,
        TimeZones timeZones)
    {
        this.conn = conn;
        this.stmt = stmt;
        this.rs = rs;
        this.range = range;
        this.timeZones = timeZones;
    }

    protected HisItem computeNext() 
    {
        try
        {
            while (rs.next())
            {
                int chunk = rs.getInt(1);
                int offset = rs.getInt(2);
                int tzId = rs.getInt(3);

                HDateTime ts = HDateTime.make(
                    chunk * PostgresArchive.MILLIS_PER_CHUNK + offset,
                    HTimeZone.make(timeZones.getName(tzId)));

                // The items returned are exclusive of start time and inclusive of end time.
                long millis = ts.millis();
                if ((range == null) || 
                    (millis > range.start.millis() && millis <= range.end.millis()))
                {
                    return new HisItem(ts, JsonCodec.parse(rs.getString(4)));
                }
            }

            return endOfData();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public void close()
    {
        try
        {
            rs.close();
            stmt.close();
            conn.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    private final Connection conn;
    private final Statement stmt;
    private final ResultSet rs;
    private final HDateTimeRange range;
    private final TimeZones timeZones;
}
