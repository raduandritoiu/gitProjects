package treehouse.db.jdbc;

import java.sql.*;
import java.util.*;
import java.util.concurrent.*;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.projecthaystack.*;

import treehouse.haystack.*;
import treehouse.haystack.io.*;
import treehouse.db.*;

public class TimeZones
{
    public TimeZones(JdbcDatabase db)
    {
        this.db = db;
        this.idFinder   = CacheBuilder.newBuilder().build(new IdFinder());
        this.nameFinder = CacheBuilder.newBuilder().build(new NameFinder());
    }

    public int getId(String name)
    {
        try
        {
            return idFinder.get(name).intValue();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public String getName(int id)
    {
        try
        {
            return nameFinder.get(id);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

////////////////////////////////////////////////////////////////
// private
////////////////////////////////////////////////////////////////

    private class IdFinder extends CacheLoader<String,Integer>
    {
        public Integer load(String name)
        {
            try
            {
                try (Connection conn = db.getConnection())
                {
                    Integer id = selectId(conn, name);
                    if (id == null)
                        id = insert(conn, name);
                    return id;
                }
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    private class NameFinder extends CacheLoader<Integer,String>
    {
        public String load(Integer id)
        {
            try
            {
                try (Connection conn = db.getConnection())
                {
                    return selectName(conn, id);
                }
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    private String selectName(Connection conn, int id) throws SQLException
    {
        String sql = "select name from timezones where id = ?";
        try (PreparedStatement prep = conn.prepareStatement(sql)) 
        {
            prep.setInt(1, id);
            try (ResultSet rs = prep.executeQuery()) {
                if (!rs.next())
                    throw new IllegalStateException("Cannot look up timezone for " + id);
                return rs.getString(1);
            }
        }
    }

    private Integer selectId(Connection conn, String name) throws SQLException
    {
        String sql = "select id from timezones where name = ?";
        try (PreparedStatement prep = conn.prepareStatement(sql)) 
        {
            prep.setString(1, name);
            try (ResultSet rs = prep.executeQuery()) {
                return rs.next() ? rs.getInt(1) : null;
            }
        }
    }

    private Integer insert(Connection conn, String name) throws SQLException
    {
        String sql = "insert into timezones (name) values (?)";
        try (PreparedStatement prep = conn.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS)) 
        {
            prep.setString(1, name);
            prep.executeUpdate();

            try (ResultSet keys = prep.getGeneratedKeys()) 
            {
                keys.next();
                return keys.getInt(1);
            }
        }
    }

////////////////////////////////////////////////////////////////
// attribs 
////////////////////////////////////////////////////////////////

    private final JdbcDatabase db;
    private final LoadingCache<String,Integer> idFinder;
    private final LoadingCache<Integer,String> nameFinder;
}
