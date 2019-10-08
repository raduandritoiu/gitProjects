import java.io.File;
import java.util.Date;

import org.tmatesoft.sqljet.core.table.SqlJetDb;

import siemens.vsst.data.io.DBFReader;
import siemens.vsst.data.io.SQLLiteCreator;
import siemens.vsst.data.io.SQLiteWriter;

public class Main
{
  public static void main(String args[]) throws Exception {
//      test_create();
      runParse();
//      CompareValves.compare();
  }
  
  
  public static void runParse() throws Exception {
//  String absolutePathBase = "C:\\radua\\work_related_projects\\simple_select\\data_bases\\2018-11-02";
//    String absolutePathBase = "C:\\radua\\home_proj\\de_la_mine\\work_related_projects\\simple_select\\data_bases\\2018-11-02";
    String absolutePathBase = "C:\\radua\\home_proj\\de_la_mine\\work_related_projects\\simple_select\\data_bases\\2019-09-22";
    
    String targetFileName = "parts_removed";
    
    
    System.out.println("=== " + absolutePathBase);
    System.out.println("=== " + absolutePathBase + "_output\\" + targetFileName + ".db");
    
    
    DBFReader reader  = new DBFReader();
    SQLiteWriter writer  = new SQLiteWriter();
    
    System.out.println("************************* START READING **************************");
    Date startDate = new Date();
    reader.startImport(absolutePathBase);
    Date endDate = new Date();
    long runningTime = (endDate.getTime() - startDate.getTime())/60000;
    System.out.println("************************* END READING (ran " + runningTime + " minutes) **************************");

    if (reader.getProducts() != null) {
      File targetFile = new File(absolutePathBase + "_output\\" + targetFileName + ".db");
      if (targetFile.exists())
        targetFile.delete();
      targetFile.createNewFile();

      System.out.println("************************* START WRITING **************************");
      startDate = new Date();
      writer.writeData(targetFile, reader);
      endDate = new Date();
      runningTime = (endDate.getTime() - startDate.getTime())/1000;
      System.out.println("************************* END WRITING (ran " + runningTime + " seconds) **************************");
    }
  }
  
  
  public static void test_create() throws Exception {
      String absolutePathBase = "C:\\radua\\work_related_projects\\simple_select\\data_bases\\2017-12-18";
      String targetFileName = "parts_new_fu_ord_1";
      File dbFile = new File(absolutePathBase + "_output\\" + targetFileName + ".db");
      
      if (dbFile.exists())
          dbFile.delete();
      
      // create the data base object
      SqlJetDb db              = SqlJetDb.open(dbFile, true);
      SQLLiteCreator dbCreator = new SQLLiteCreator();
      dbCreator.prepareDatabase(db);    
      db.close();
  }
}
