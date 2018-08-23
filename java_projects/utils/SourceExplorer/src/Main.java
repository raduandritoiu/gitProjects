import java.io.File;

import explore.Compare;
import explore.Explorer;
import utils.PrintUtils;

public class Main
{
  public static void main(String[] args)
  {
    compareFiles();
//    exploreSource();
//    compareSources();
  }
  
  private static void compareFiles()
  {
    String dirPath = "C:/Users/radua/Desktop/work work/branding/";
    String filePath1 = "setup_2.exe";
    String filePath2 = "setup_3.exe";
    File file1 = new File(dirPath + filePath1);
    File file2 = new File(dirPath + filePath2);
    
    boolean result = Compare.compareFilesAsBinary(file1, file2, "", false);
    if (result)
      PrintUtils.print(0, "Files are equal!");
  }
  
  
  private static void exploreSource()
  {
    String mainFolder = "pneumatics";
    String basePath = "C:/radua/svn_repositories/RaduA/DataTool/VSSTDataInterpolater/src/siemens/vsst/data/enumeration/";
    
    File mainFile = new File(basePath + mainFolder);
    Explorer.exploreDirectory(0, mainFolder, mainFile);
  }
  
  
  private static void compareSources()
  {
    String dirPath = "/bbi";
    String dirBasePath1 = "C:/radua/git_repositories/baconnector/bbiParser/src";
    String dirBasePath2 = "C:/Users/radua/Desktop/mofo/src";
    File mainDir1 = new File(dirBasePath1 + dirPath);
    File mainDir2 = new File(dirBasePath2 + dirPath);
    
    boolean result = Compare.compareDirs(mainDir1, mainDir2, "");
    if (result)
      PrintUtils.print(0, "Directories are equal!");
  }
}
