package basic;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

public class TestPrint
{
  public static void printResultSetWithMeta(ResultSet result) throws SQLException
  {
    ResultSetMetaData meta = result.getMetaData();
    int cols = meta.getColumnCount();
    String str = "";
    for (int i = 1; i <= cols; i++)
      str += meta.getColumnName(i)+ "\t ";
    System.out.println(str);
    
    while (result.next())
    {
      str = "";
      for (int i = 1; i <= cols; i++)
      {
        int t = meta.getColumnType(i);
        if (t == Types.INTEGER)
          str += result.getInt(i) + "\t ";
        else if (t == Types.VARCHAR)
          str += result.getString(i) + "\t ";
        else if (t == Types.BINARY)
          str += result.getBoolean(i) + "\t ";
        else if (t == Types.LONGVARBINARY)
          str += "BLOB\t ";
        else
          str += "UNKNOWN_"+t+"\t ";
      }
      System.out.println(str);
    }
  }
  
  
  public static void printRowWithMeta(ResultSet result) throws SQLException
  {
    ResultSetMetaData meta = result.getMetaData();
    int cols = meta.getColumnCount();
    String str = "";
    for (int i = 1; i <= cols; i++)
    {
      int t = meta.getColumnType(i);
      if (t == Types.INTEGER)
        str += result.getInt(i) + "\t ";
      else if (t == Types.VARCHAR)
        str += result.getString(i) + "\t ";
      else if (t == Types.BINARY)
        str += result.getBoolean(i) + "\t ";
      else if (t == Types.LONGVARBINARY)
        str += "BLOB\t ";
      else
        str += "UNKNOWN_"+t+"\t ";
    }
    System.out.println(str);
  }
  
  
  public static void printSQLException(SQLException ex) 
  {
    for (Throwable e : ex) 
    {
      if (e instanceof SQLException) 
      {
        e.printStackTrace(System.err);
        System.err.println("SQLState: " + ((SQLException)e).getSQLState());
        System.err.println("Error Code: " + ((SQLException)e).getErrorCode());
        System.err.println("Message: " + e.getMessage());
        Throwable t = ex.getCause();
        while (t != null) 
        {
          System.out.println("Cause: " + t);
          t = t.getCause();
        }
      }
    }
  }
}
