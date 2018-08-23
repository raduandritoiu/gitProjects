package basic;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class ManiputalingTables
{
  public static void test() throws SQLException
  {
    Connection conn = CreatingConnection.useDriverMng_noArgs();
    createTable_small(conn, "test_unu");
    addRowsIn_small(conn, "test_unu");
  }
  
  public static void addRowsIn_small(Connection conn, String tableName) throws SQLException
  {
    String dbName = conn.getCatalog();
    Statement st = conn.createStatement();
    int r = st.executeUpdate("insert into "+dbName+"."+tableName+"(name, value) values(\"mama\", 5432)");
    System.out.println("Executed with " + r);
  }
  
  public static void createTable_small(Connection conn, String tableName) throws SQLException
  {
    String dbName = conn.getCatalog();
    
    String sql = "create table " + dbName + "." + tableName +
        " (id_"+tableName+" INT NOT NULL AUTO_INCREMENT, " +
        "name VARCHAR(45) NOT NULL, " +
        "value INT NOT NULL, " +
        "PRIMARY KEY (id_"+tableName+"))";
    
    Statement st = conn.createStatement();
    int resp = st.executeUpdate(sql);
    System.out.println("Executed with " + resp);
  }
  
  
  public static void createTable_intricate(Connection conn, String tableName) throws SQLException
  {
    String dbName = conn.getCatalog();
    
    String sql = "create table " + dbName + "." + tableName +
        " (id_"+tableName+" INT NOT NULL AUTO_INCREMENT, " +
        "NAME VARCHAR(45) NOT NULL, " +
        "CaMmElCaSe INT NOT NULL, " +
        "description VARCHAR(45) NOT NULL DEFAULT 'yuhuuuu', " +
        "Value int null, " +
        "timeStamp Datetime null, " +
        "PRIMARY KEY (id_"+tableName+"), " +
        "UNIQUE INDEX id_"+tableName+" (id_"+tableName+" ASC), " +
        "UNIQUE INDEX NAME (NAME ASC))";
    
    Statement st = conn.createStatement();
    int resp = st.executeUpdate(sql);
    System.out.println("Executed with " + resp);
  }

  
  public static void createTable_Two(Connection conn, String tableNameOne, String tableNameTwo) throws SQLException
  {
    String dbName = conn.getCatalog();
    Statement st = conn.createStatement();
    
    String sql1 = "create table " + dbName + "." + tableNameOne +
        " (id_"+tableNameOne+" INT NOT NULL AUTO_INCREMENT, " +
        "name VARCHAR(45) NOT NULL , " +
        "value INT NOT NULL, " +
        "PRIMARY KEY (id_"+tableNameOne+"), " +
        "UNIQUE INDEX id_"+tableNameOne+" (id_"+tableNameOne+" ASC))";
    
    int resp = st.executeUpdate(sql1);
    System.out.println("Executed with " + resp);
    
    String sql2 = "create table "+dbName+"."+tableNameTwo +
        " (id_"+tableNameTwo+" INT NOT NULL AUTO_INCREMENT, " +
        "name VARCHAR(45) NOT NULL , " +
        "value INT NOT NULL, " +
        "id_"+tableNameOne+"_ref INT NOT NULL, " +
        "PRIMARY KEY (id_"+tableNameTwo+"), " +
        "UNIQUE INDEX id_"+tableNameTwo+" (id_"+tableNameTwo+" ASC), " +
        "FOREIGN KEY (id_"+tableNameOne+"_ref) REFERENCES "+tableNameOne+"(id_"+tableNameOne+"))";
    
    resp = st.executeUpdate(sql2);
    System.out.println("Executed with " + resp);
  }
  

  public static void createTable_Cross(Connection conn, String tableNameOne, String tableNameTwo) throws SQLException
  {
    String dbName = conn.getCatalog();
    Statement st = conn.createStatement();
    
    String sql1 = "create table " + dbName + "." + tableNameOne +
        " (id_"+tableNameOne+" INT NOT NULL AUTO_INCREMENT, " +
        "name VARCHAR(45) NOT NULL , " +
        "value INT NOT NULL, " +
        "PRIMARY KEY (id_"+tableNameOne+"), " +
        "UNIQUE INDEX id_"+tableNameOne+" (id_"+tableNameOne+" ASC))";
    int resp = st.executeUpdate(sql1);
    System.out.println("Executed with " + resp);
    
    String sql2 = "create table "+dbName+"."+tableNameTwo +
        " (id_"+tableNameTwo+" INT NOT NULL AUTO_INCREMENT, " +
        "name VARCHAR(45) NOT NULL , " +
        "value INT NOT NULL, " +
        "PRIMARY KEY (id_"+tableNameTwo+"), " +
        "UNIQUE INDEX id_"+tableNameTwo+" (id_"+tableNameTwo+" ASC))";
    resp = st.executeUpdate(sql2);
    System.out.println("Executed with " + resp);
    
    String sql3 = "create table "+dbName+"."+tableNameOne+"_"+tableNameTwo +
        " (id_cross INT NOT NULL AUTO_INCREMENT, " +
        "name VARCHAR(45) NOT NULL , " +
        "value INT NOT NULL, " +
        "id_"+tableNameOne+"_ref INT NOT NULL, " +
        "id_"+tableNameTwo+"_ref INT NOT NULL, " +
        "PRIMARY KEY (id_cross), " +
        "UNIQUE INDEX id_cross (id_cross ASC), " +
        "FOREIGN KEY (id_"+tableNameOne+"_ref) REFERENCES "+tableNameOne+"(id_"+tableNameOne+"), "+
        "FOREIGN KEY (id_"+tableNameTwo+"_ref) REFERENCES "+tableNameTwo+"(id_"+tableNameTwo+"))";
    resp = st.executeUpdate(sql3);
    System.out.println("Executed with " + resp);
  }
  
  
  public static void deleteTable(Connection conn, String tableName) throws SQLException
  {
    String dbName = conn.getCatalog();
    String sql = "drop table " + dbName + "." + tableName;
    Statement st = conn.createStatement();
    int resp = st.executeUpdate(sql);
    System.out.println("Executed with " + resp);
  }
}
