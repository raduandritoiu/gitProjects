package basic;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ManipulatingData
{
  public static String tableName = "person";
  
  public static void test() throws SQLException
  {
    Connection conn = CreatingConnection.useDriverMng_noArgs();
//    verifySupport(conn);
    
    QuickExample.run();
//
//    testResultSet_get_2(conn);
  }
  
  
  public static void verifySupport( Connection conn) throws SQLException
  {
    DatabaseMetaData meta = conn.getMetaData();
    
    boolean b = meta.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY);
    System.out.println("Supports TYPE_FORWARD_ONLY: " + b);
    
    b = meta.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE);
    System.out.println("Supports TYPE_SCROLL_INSENSITIVE: " + b);
    
    b = meta.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE);
    System.out.println("Supports TYPE_SCROLL_SENSITIVE: " + b);
    
    b = meta.supportsResultSetConcurrency(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
    System.out.println("Supports TYPE_FORWARD_ONLY - CONCUR_READ_ONLY: " + b);
    b = meta.supportsResultSetConcurrency(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
    System.out.println("Supports TYPE_FORWARD_ONLY - CONCUR_UPDATABLE: " + b);
    
    b = meta.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    System.out.println("Supports TYPE_SCROLL_INSENSITIVE - CONCUR_READ_ONLY: " + b);
    b = meta.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
    System.out.println("Supports TYPE_SCROLL_INSENSITIVE - CONCUR_UPDATABLE: " + b);
    
    b = meta.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
    System.out.println("Supports TYPE_SCROLL_SENSITIVE - CONCUR_READ_ONLY: " + b);
    b = meta.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    System.out.println("Supports TYPE_SCROLL_SENSITIVE - CONCUR_UPDATABLE: " + b);
    
    b = meta.supportsResultSetHoldability(ResultSet.HOLD_CURSORS_OVER_COMMIT);
    System.out.println("Supports HOLD_CURSORS_OVER_COMMIT: " + b);
    
    b = meta.supportsResultSetHoldability(ResultSet.CLOSE_CURSORS_AT_COMMIT);
    System.out.println("Supports CLOSE_CURSORS_AT_COMMIT: " + b);
  }
  
  
  public static void testResultSet_get_1(Connection conn) throws SQLException
  {
    // daca fac disable la asta, pot controla eu cu connection.commit() cand se face commit
    conn.setAutoCommit(false);
    
    // daca il creez cu CONCUR_READ_ONLY - nu pot modifica -> va da eroare/crapa
    // merge cu orice fel de ResultSet.TYPE
    Statement st = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE, ResultSet.CLOSE_CURSORS_AT_COMMIT);
    // sa vedem daca le ia pe bucati, cate 10 10
    st.setMaxRows(10);
    boolean b = st.execute("Select * from " + tableName);
    ResultSet resultSet = st.getResultSet();
    while (resultSet != null)
    {
      System.out.println(" -start");
      while (resultSet.next())
      {
        TestPrint.printRowWithMeta(resultSet);
      }
      // le ia pe PULA, nu mai ia nimic
      st.getMoreResults();
      resultSet = st.getResultSet();
      System.out.println(" -done");
    }    
    
    conn.commit();
    conn.setAutoCommit(true);
  }
  
  
  public static void testResultSet_get_2(Connection conn) throws SQLException
  {
    // daca fac disable la asta, pot controla eu cu connection.commit() cand se face commit
    conn.setAutoCommit(false);
    
    // daca il creez cu CONCUR_READ_ONLY - nu pot modifica -> va da eroare/crapa
    // merge cu orice fel de ResultSet.TYPE
    Statement st = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE, ResultSet.CLOSE_CURSORS_AT_COMMIT);
    ResultSet resultSet = st.executeQuery("Select * from " + tableName + " where " + tableName + ".idperson > 25");
    while (resultSet.next())
    {
      TestPrint.printRowWithMeta(resultSet);
    }
    
    conn.commit();
    conn.setAutoCommit(true);
  }
  
  
  public static void testResultSet_update(Connection conn) throws SQLException
  {
    // daca fac disable la asta, pot controla eu cu connection.commit() cand se face commit
    conn.setAutoCommit(false);
    
    // daca il creez cu CONCUR_READ_ONLY - nu pot modifica -> va da eroare/crapa
    // merge cu orice fel de ResultSet.TYPE
    Statement st = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE, ResultSet.CLOSE_CURSORS_AT_COMMIT);
    ResultSet resultSet = st.executeQuery("Select * from " + tableName);
    while (resultSet.next())
    {
      int v = resultSet.getInt("salary");
      if (v > 10000)
      {
        resultSet.updateInt("salary", v / 10000);
        resultSet.updateRow();
      }
      TestPrint.printRowWithMeta(resultSet);
    }
    
    conn.commit();
    conn.setAutoCommit(true);
  }
  
  
  public static void testResultSet_insert(Connection conn) throws SQLException
  {
    // daca fac disable la asta, pot controla eu cu connection.commit() cand se scrie in baza de date
    conn.setAutoCommit(false);
    
    // daca il creez cu CONCUR_READ_ONLY - nu pot modifica -> va da eroare/crapa
    // merge cu orice fel de ResultSet.TYPE
    
    // trebuie sa fie 
    Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.CLOSE_CURSORS_AT_COMMIT);
    ResultSet resultSet = st.executeQuery("select * from " + tableName);
    
    for (int i = 27; i < 30; i++)
    {
      resultSet.moveToInsertRow();
      resultSet.updateString("name", "pers_" + i);
      resultSet.updateInt("age", 40 + i);
      resultSet.updateInt("salary", 111111 * i);
      resultSet.updateInt("adr", 3);
      resultSet.updateInt("work", 22);
      // de regula aceasta fc trimite modificarile in DB ; daca auto-commit e true
      resultSet.insertRow();
    }    
    
    // fac commit manual daca auto-commit e false; OBS: crapa daca altfel
    conn.commit();
    conn.setAutoCommit(true);
  }  
  
  
  public static void testResultSet_delete(Connection conn) throws SQLException
  {
    // daca fac disable la asta, pot controla eu cu connection.commit() cand se scrie in baza de date
    conn.setAutoCommit(false);
    
    // daca il creez cu CONCUR_READ_ONLY - nu pot modifica -> va da eroare/crapa
    // merge cu orice fel de ResultSet.TYPE
    
    // trebuie sa fie 
    Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.CLOSE_CURSORS_AT_COMMIT);
    ResultSet resultSet = st.executeQuery("select * from " + tableName);
    
    while (resultSet.next())
    {
      int id = resultSet.getInt(1);
      if (id > 37 && id < 70)
      {
        System.out.println("person: " + resultSet.getString("name"));
        resultSet.deleteRow();
      }
    }
    
    // fac commit manual daca auto-commit e false; OBS: crapa daca altfel
    conn.commit();
    conn.setAutoCommit(true);
  }  
  
  
  public static void testBatch_insert(Connection conn) throws SQLException
  {
    // daca fac disable la asta, pot controla eu cu connection.commit() cand se scrie in baza de date
    conn.setAutoCommit(false);
    
    // daca il creez cu CONCUR_READ_ONLY - nu pot modifica -> va da eroare/crapa
    // merge cu orice fel de ResultSet.TYPE
    
    Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.CLOSE_CURSORS_AT_COMMIT);
    
    for (int i = 34; i < 37; i++)
    {
      st.addBatch("insert into test_jdbc."+tableName+" (name, age, salary, adr, work) values(\"tst_"+i+"\", "+(i+30)+", "+(i*11000)+", 2, 19)");
    }
    
    // daca auto-commit este enabled datele se scriu la "executeBatch", daca nu se scriu la "commit()"
    int[] rs = st.executeBatch();
    conn.commit();
    conn.setAutoCommit(true);
  }
  
  
  public static void testBatch_delete(Connection conn) throws SQLException
  {
    // daca fac disable la asta, pot controla eu cu connection.commit() cand se scrie in baza de date
    conn.setAutoCommit(false);
    
    // daca il creez cu CONCUR_READ_ONLY - nu pot modifica -> va da eroare/crapa
    // merge cu orice fel de ResultSet.TYPE
    
    Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.CLOSE_CURSORS_AT_COMMIT);
    
    for (int i = 60; i < 65; i++)
    {
      st.addBatch("delete from test_jdbc."+tableName+" where idPerson = " + i);
    }
    
    // daca auto-commit este enabled datele se scriu la "executeBatch", daca nu se scriu la "commit()"
    int[] rs = st.executeBatch();
    conn.commit();
    conn.setAutoCommit(true);
  }
  
  
  public static void testPrepared_get(Connection conn) throws SQLException
  {
    // daca fac disable la asta, pot controla eu cu connection.commit() cand se face commit
    conn.setAutoCommit(false);
    
    // daca il creez cu CONCUR_READ_ONLY - nu pot modifica -> va da eroare/crapa
    // merge cu orice fel de ResultSet.TYPE
    PreparedStatement st = conn.prepareStatement("Select * from " + tableName + " where " + tableName + ".idperson > ?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE, ResultSet.CLOSE_CURSORS_AT_COMMIT);
    st.setInt(1, 25);
    ResultSet resultSet = st.executeQuery();
    while (resultSet.next())
    {
      TestPrint.printRowWithMeta(resultSet);
    }
    
    st.setInt(1, 45);
    resultSet = st.executeQuery();
    while (resultSet.next())
    {
      TestPrint.printRowWithMeta(resultSet);
    }
    
    conn.commit();
    conn.setAutoCommit(true);
  }
}