package basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

import javax.sql.DataSource;
import javax.sql.PooledConnection;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class CreatingConnection
{
  private static HashMap<String, Object> ctx = new HashMap<>();
  public static String url_full = "jdbc:mysql://192.168.100.2:3306/test_jdbc?user=radu&password=radu";
  public static String url = "jdbc:mysql://192.168.100.2:3306/test_jdbc";
  public static String user = "radu";
  public static String pass = "radu";
  
  static
  {
    try
    {
      setUpDataSource_normal();
      setUpDataSource_pool();
    }
    catch (SQLException e)
    { System.out.println("Ups: " + e);}
  }
  
  public static void test() throws SQLException
  {
//    Connection conn1 = useDriverMng_noArgs();
//    Connection conn2 = useDriverMng_withArgs();
//    Connection conn3 = useDriverMng_withProps();
    
    setUpDataSource_normal();
    setUpDataSource_pool();
    
//    Connection conn4 = useDataSource_normal();
    Connection conn = useDataSource_pool();
    
    ResultSet result = QuickExample.unQueryUsor(conn);
    QuickExample.printResultWithMeta(result);
    result.close();
    conn.close();
  }
  
  
  public static Connection useDriverMng_noArgs() throws SQLException
  {
    Connection conn = DriverManager.getConnection(url_full);
    return conn;
  }
  
  public static Connection useDriverMng_withArgs() throws SQLException
  {
    Connection conn = DriverManager.getConnection(url, user, pass);
    return conn;
  }
  
  public static Connection useDriverMng_withProps() throws SQLException
  {
    Properties props = new Properties();
    props.put("user", "radu");
    props.put("password", "radu");
    Connection conn = DriverManager.getConnection(url, props);
    return conn;
  }
  
  
  public static void setUpDataSource_normal() throws SQLException
  {
    MysqlDataSource mySqlDs = new MysqlDataSource();
    mySqlDs.setURL(url);
    mySqlDs.setUser("radu");
    mySqlDs.setPassword("radu");
    
    ctx.put("ds_normal", mySqlDs);
  }
  
  public static void setUpDataSource_pool() throws SQLException
  {
    MysqlConnectionPoolDataSource poolDs = new MysqlConnectionPoolDataSource();
    poolDs.setURL(url);
    poolDs.setUser("radu");
    poolDs.setPassword("radu");
    
    ctx.put("ds_pooled", poolDs);
  }
  
  public static Connection useDataSource_normal() throws SQLException
  {
    DataSource ds1 = (DataSource) ctx.get("ds_normal");
    Connection conn = ds1.getConnection();
    return conn;
  }
  
  public static Connection useDataSource_pool() throws SQLException
  {
    MysqlConnectionPoolDataSource pds = (MysqlConnectionPoolDataSource) ctx.get("ds_pooled");
    PooledConnection pc = pds.getPooledConnection();
    Connection conn = pc.getConnection();
    // sau 
    // Connection conn = pds.getConnection();
    return conn;
  }
}
