package treehouse.db.jdbc;

import java.sql.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.projecthaystack.*;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import treehouse.haystack.*;
import treehouse.haystack.io.*;
import treehouse.db.*;

/**
  * JdbcDatabase models a JDBC-based database
  */
public abstract class JdbcDatabase extends Database
{
    public JdbcDatabase()
    {
    }

    /**
      * Return a JDBC Connection.  Don't forget to close it!
      */
    public abstract Connection getConnection();

    /**
      * Create the table if it does not exist.
      */
    public static void ensureTable(
        Connection conn,
        String tableName, 
        String[] ddl) 
    throws SQLException
    {
        // this seems to be necessary
        String lower = tableName.toLowerCase();

        try(ResultSet rs = conn.getMetaData().getTables(null, null, lower, null))
        {
            if (!rs.next()) 
            {
                try (Statement stmt = conn.createStatement()) 
                {
                    for (int i = 0; i < ddl.length; i++)
                    {
                        LOG.info("executing '" + ddl[i] + "'");
                        stmt.execute(ddl[i]);
                    }
                }
            }
        }
    }

////////////////////////////////////////////////////////////////
// attribs
////////////////////////////////////////////////////////////////

    private static final Logger LOG = LoggerFactory.getLogger(JdbcDatabase.class);
}
