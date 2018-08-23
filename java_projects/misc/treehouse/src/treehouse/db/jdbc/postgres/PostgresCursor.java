package treehouse.db.jdbc.postgres;

import java.sql.*;
import java.util.*;

import com.google.common.collect.AbstractIterator;
import org.projecthaystack.*;

import treehouse.haystack.*;
import treehouse.haystack.io.*;
import treehouse.db.*;

class PostgresCursor 
    extends AbstractIterator<HDict>
    implements Cursor 
{
    PostgresCursor(PostgresTable table, Connection conn, Statement stmt, ResultSet rs)
    {
        this.table = table;
        this.conn = conn;
        this.stmt = stmt;
        this.rs = rs;
    }

    protected HDict computeNext() 
    {
        try
        {
            if (rs.next())
            {
                HDict record = (HDict) JsonCodec.parse(rs.getString(1));
                return table.computeDis(record);
            }
            else
            {
                return endOfData();
            }
        }
        catch (SQLException e)
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

    private final PostgresTable table;
    private final Connection conn;
    private final Statement stmt;
    private final ResultSet rs;
}
