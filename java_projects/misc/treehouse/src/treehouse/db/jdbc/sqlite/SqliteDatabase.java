package treehouse.db.jdbc.sqlite;

import java.sql.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.projecthaystack.*;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import treehouse.db.jdbc.*;
import treehouse.haystack.*;
import treehouse.haystack.io.*;
import treehouse.db.*;

/**
  * SqliteDatabase models a SqlLite database
  */
public class SqliteDatabase extends JdbcDatabase
{
    public SqliteDatabase(String fileName)
    {
        this.fileName = fileName;
        this.jdbcUrl = "jdbc:sqlite:" + fileName;
    }

    /**
      * Implementation hook for open().
      */
    protected void doOpen()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            LOG.info("opening " + jdbcUrl);
            //ensureTimeZones();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
      * Implementation hook for close().
      */
    protected void doClose()
    {
        LOG.info("closing " + jdbcUrl);
    }

    /**
      * Implementation hook for addTable().
      */
    protected Table doAddTable(String name)
    {
        return new SqliteTable(this, name);
    }

    /**
      * Implementation hook for addArchive().
      */
    protected Archive doAddArchive(Table parent, String name)
    {
        throw new UnsupportedOperationException(
            "SqlLite does not support Archive.");
    }

////////////////////////////////////////////////////////////////
// JdbcDatabase
////////////////////////////////////////////////////////////////

    /**
      * Return a JDBC Connection.  Don't forget to close it!
      */
    public Connection getConnection()
    {
        try
        {
            return DriverManager.getConnection(jdbcUrl);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

////////////////////////////////////////////////////////////////
// private
////////////////////////////////////////////////////////////////

//    private void ensureTimeZones() throws SQLException
//    {
//        try(Connection conn = getConnection();
//            ResultSet rs = conn.getMetaData().getTables(
//                null, null, "timezones", null))
//        {
//            if (!rs.next()) {
//                try (Statement stmt = conn.createStatement()) {
//                    stmt.execute("create table timezones (id integer primary key, name text);");
//                    stmt.execute("create index on timezones (name);");
//                }
//            }
//        }
//    }

////////////////////////////////////////////////////////////////
// attribs
////////////////////////////////////////////////////////////////

    private static final Logger LOG = LoggerFactory.getLogger(SqliteDatabase.class);

    private final String fileName;
    private final String jdbcUrl;
}
