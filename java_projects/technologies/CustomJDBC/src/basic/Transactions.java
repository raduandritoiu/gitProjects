package basic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Transactions
{
  public static String tableName = "person";
  
  public static void runThreads() throws Exception
  {
    ExecutorService pool = Executors.newFixedThreadPool(5);
    Connection conn = CreatingConnection.useDriverMng_noArgs();
    
    
    int n = 2;
    TaskWrapper[] tasks = new TaskWrapper[n];
    

    new GetRecords().run(conn);
    
//    tasks[0] = new TaskWrapper(conn, new GetRecords());
//    tasks[1] = new TaskWrapper(conn, new GetRecords());
//    tasks[2] = new TaskWrapper(conn, new GetRecords());
    tasks[0] = new TaskWrapper(conn, new AddSalary());
    tasks[1] = new TaskWrapper(conn, new FlipSalary());
    
    
    for (int i = 0; i < n; i++)
      pool.submit(tasks[i]);
    pool.shutdown();
    pool.awaitTermination(20, TimeUnit.HOURS);
    
    new GetRecords().run(conn);
    for (int i = 0; i < n; i++)
      if (tasks[i].sqlEx != null)
      {
        TestPrint.printSQLException(tasks[i].sqlEx);
        System.out.println("pula mea!!!! ");
      }
  }
  
  private static class TaskWrapper implements Runnable
  {
    public Connection conn;
    public SqlTask sqlTask;
    public SQLException sqlEx;
    
    public TaskWrapper(Connection nConn, SqlTask nSqlTask)
    {
      conn = nConn;
      sqlTask = nSqlTask;
    }
    
    public void run()
    {
      try
      { 
        conn = CreatingConnection.useDriverMng_noArgs();
        sqlTask.run(conn);
      } 
      catch (SQLException ex)
      { sqlEx = ex; }
    }
  }
  
  private static interface SqlTask
  {
    void run(Connection conn) throws SQLException;
  }
  
  
  private static class GetRecords implements SqlTask
  {
    public void run(Connection conn) throws SQLException
    {
      // daca fac disable la asta, pot controla eu cu connection.commit() cand se face commit
//      conn.setAutoCommit(false);
//      boolean auto = conn.getAutoCommit();
      
      // daca il creez cu CONCUR_READ_ONLY - nu pot modifica -> va da eroare/crapa
      // merge cu orice fel de ResultSet.TYPE
      Statement st = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE, ResultSet.CLOSE_CURSORS_AT_COMMIT);
      ResultSet resultSet = st.executeQuery("Select * from " + tableName);
      while (resultSet.next())
      {
        TestPrint.printRowWithMeta(resultSet);
      }

      System.out.println("==================================================================");

//      conn.commit();
//      conn.setAutoCommit(true);
    }
  }
  
  
  public static class FlipSalary implements SqlTask 
  {
    public void run(Connection conn) throws SQLException
    {
//      try { Thread.sleep(4000); }
//      catch (Exception ex) {System.out.println(" $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ ");}
      
      // daca fac disable la asta, pot controla eu cu connection.commit() cand se face commit
//      conn.setAutoCommit(false);
      boolean auto = conn.getAutoCommit();
      
      
      // daca il creez cu CONCUR_READ_ONLY - nu pot modifica -> va da eroare/crapa
      // merge cu orice fel de ResultSet.TYPE
      System.out.println("* read FLIP");
      Statement st = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE, ResultSet.CLOSE_CURSORS_AT_COMMIT);
      ResultSet resultSet = st.executeQuery("Select * from " + tableName);
      System.out.println("* has read FLIP");
      
      System.out.println("* write FLIP");
      while (resultSet.next())
      {
//        TestPrint.printRowWithMeta(resultSet);
        int v = resultSet.getInt("salary");
        resultSet.updateInt("salary", (v > 0) ? (v - 100000) : (v + 100000));
        resultSet.updateRow();
      }
      System.out.println("* has write FLIP");
      
      System.out.println("* commit FLIP");
//      conn.commit();
      System.out.println("* has commit FLIP");
//      conn.setAutoCommit(true);
    }
  }  
  
  
  public static class AddSalary implements SqlTask 
  {
    public void run(Connection conn) throws SQLException
    {
      // daca fac disable la asta, pot controla eu cu connection.commit() cand se face commit
//      conn.setAutoCommit(false);
      boolean auto = conn.getAutoCommit();
      
      
      // daca il creez cu CONCUR_READ_ONLY - nu pot modifica -> va da eroare/crapa
      // merge cu orice fel de ResultSet.TYPE
      System.out.println("* read ADD");
      Statement st = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE, ResultSet.CLOSE_CURSORS_AT_COMMIT);
      ResultSet resultSet = st.executeQuery("Select * from " + tableName);
      System.out.println("* has read ADD");
      
      System.out.println("* write ADD");
      while (resultSet.next())
      {
//        TestPrint.printRowWithMeta(resultSet);
        int v = resultSet.getInt("salary");
        resultSet.updateInt("salary", v + 20000);
        resultSet.updateRow();
      }
      System.out.println("* has write ADD");
      
//      try { Thread.sleep(12000); }
//      catch (Exception ex) {System.out.println(" $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ ");}
      
      System.out.println("* commit ADD");
//      conn.commit();
      System.out.println("* has commit ADD");
//      conn.setAutoCommit(true);
    }
  }
}
