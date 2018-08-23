package treehouse.db.jdbc.postgres;

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
  * PostgresDatabase models a PostGres Database Instance
  */
public class PostgresDatabase extends JdbcDatabase
{
    public PostgresDatabase(
        String host,
        int    port,
        String database,
        String username,
        String password)
    {
        this.host      = host;    
        this.port      = port;    
        this.database  = database; 
        this.jdbcUrl = "jdbc:postgresql://" + host + ":" + port + "/" + database;

        this.username  = username; 
        this.password  = password; 
    }

    /**
      * Implementation hook for open().
      */
    protected void doOpen()
    {
        try
        {
            Class.forName("org.postgresql.Driver");

            LOG.info("opening " + jdbcUrl);

            BoneCPConfig config = new BoneCPConfig(); 
            config.setJdbcUrl(jdbcUrl);
            config.setUsername(username);
            config.setPassword(password);

            this.connPool = new BoneCP(config);

            ensureTimeZones();
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

        connPool.close();
        connPool = null;
    }

    /**
      * Implementation hook for addTable().
      */
    protected Table doAddTable(String name)
    {
        return new PostgresTable(this, name);
    }

    /**
      * Implementation hook for addArchive().
      */
    protected Archive doAddArchive(Table parent, String name)
    {
        return new PostgresArchive(this, (PostgresTable) parent, name);
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
            return connPool.getConnection();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

////////////////////////////////////////////////////////////////
// private
////////////////////////////////////////////////////////////////

    private void ensureTimeZones() throws SQLException
    {
        try(Connection conn = getConnection();
            ResultSet rs = conn.getMetaData().getTables(
                null, null, "timezones", null))
        {
            if (!rs.next()) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute("create table timezones (id serial primary key, name text);");
                    stmt.execute("create index on timezones (name);");
                }
            }
        }
    }

////////////////////////////////////////////////////////////////
// attribs
////////////////////////////////////////////////////////////////

    private static final Logger LOG = LoggerFactory.getLogger(PostgresDatabase.class);

    private final String host;
    private final int    port;
    private final String database;
    private final String jdbcUrl;

    private final String username;
    private final String password;

    private BoneCP connPool;
}
