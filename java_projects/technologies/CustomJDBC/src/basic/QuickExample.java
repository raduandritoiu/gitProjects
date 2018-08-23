package basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class QuickExample
{
  public static void run() throws SQLException
  {
    String user = "radu";
    String pass = "radu";
    String url = "jdbc:mysql://192.168.100.3:3306/test_jdbc";
    
    // create connection - cele doua variante sunt echivalente
//    Connection connect = DriverManager.getConnection(url + "?user=" + user + "&password=" + pass);
    Connection connect = DriverManager.getConnection(url, user, pass);
    
    // get results
//    ResultSet resultSet = unQueryCelMaiMare(connect);
    ResultSet resultSet = unQueryUsor(connect);
    // print them 
    printResultWithMeta(resultSet);
    
    System.out.println("foo");
  }
  
  
  public static ResultSet unQueryUsor(Connection connect) throws SQLException
  {
    // create statement
    Statement statement = connect.createStatement();
    // get results
    ResultSet resultSet = statement.executeQuery("Select * from person");
    // return them
    return resultSet;
  }
  
  public static ResultSet unQueryCelMaiMare(Connection connect) throws SQLException
  {
    // create statement
    Statement statement = connect.createStatement();
    // get results
    ResultSet resultSet = statement.executeQuery("SELECT * from person " +
        "JOIN address ON person.adr = address.idaddress " +
        "JOIN actual_dept ON person.work = actual_dept.idactual_dept " +
        "JOIN dept_type ON actual_dept.type = dept_type.iddept_type " +
        "JOIN company ON actual_dept.company = company.idcompany");
    // return them
    return resultSet;
  }
  
  public static void printResultWithNames(ResultSet result) throws SQLException
  {
    System.out.println("id\t name\t age\t adr\t salary\t work");
    while (result.next())
    {
      int id = result.getInt("idPerson");
      String name = result.getString("name");
      int age = result.getInt("age");
      int salary = result.getInt("salary");
      int adr = result.getInt("adr");
      int work = result.getInt("work");
      System.out.println(id + "\t " + name  + "\t " + age + "\t " + adr + "\t " + salary + "\t " + work);
    }
  }
  
  public static void printResultWithIdx(ResultSet result) throws SQLException
  {
    System.out.println("id\t name\t age\t adr\t salary\t work");
    while (result.next())
    {
      int id = result.getInt(1);
      String name = result.getString(2);
      int age = result.getInt(3);
      int salary = result.getInt(4);
      int adr = result.getInt(5);
      int work = result.getInt(6);
      System.out.println(id + "\t " + name  + "\t " + age + "\t " + adr + "\t " + salary + "\t " + work);
    }
  }
  
  public static void printResultWithMeta(ResultSet result) throws SQLException
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
}
