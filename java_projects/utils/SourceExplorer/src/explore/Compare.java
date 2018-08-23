package explore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;

import utils.PrintUtils;

public class Compare
{
  public static boolean compareDirs(File dir1, File dir2, String relPath)
  {
    if (!dir1.isDirectory())
      return false;
    if (!dir2.isDirectory())
      return false;
    
    if (!dir1.getName().equals(dir2.getName()))
    {
      PrintUtils.printParagraph(0, "Directories have different names: " +
          "<"+relPath+dir1.getName()+">    -    " +
          "<"+relPath+dir2.getName()+">");
      return false;
    }
    
    File[] files1 = dir1.listFiles();
    File[] files2 = dir2.listFiles();
    
    if (files1.length != files2.length)
    {
      PrintUtils.printParagraph(0, "Directories    <"+relPath+dir1.getName()+">    have different number of files : " +
          "<"+files1.length+">    -    " +
          "<"+files2.length+">");
    }
    
    for (int i = 0; i < files1.length; i++)
    {
      if (!files1[i].getName().equals(files2[i].getName()))
      {
        PrintUtils.printParagraph(0, "Directories   <"+relPath+dir1.getName()+">   different files on position "+i+" : " +
            "<"+files1[i].getName()+">    -    " +
            "<"+files2[i].getName()+">");
        return false;
      }
      
      if (files1[i].isDirectory() ^ files2[i].isDirectory())
      {
        PrintUtils.printParagraph(0, "Directories   <"+relPath+dir1.getName()+">   have different types of files on position "+i+" : " +
            "<"+files1[i].getName()+">    -    " +
            "<"+files2[i].getName()+">");
        return false;
      }
    }
    
    boolean finalRes = true;
    for (int i = 0; i < files1.length; i++)
    {
      boolean res = true;
      if (files1[i].isDirectory())
      {
        res = compareDirs(files1[i], files2[i], relPath+dir1.getName()+"/");
      }
      else
      {
        res = compareFilesAsText(files1[i], files2[i], relPath+dir1.getName()+"/", true);
      }
      
      if (!res)
      {
        finalRes = false;
        PrintUtils.printParagraph(1, "***");
//        return false;
      }
    }
    
    return finalRes;
  }
  
  
  public static boolean compareFilesAsText(File file1, File file2, String relPath, boolean stopOnErr)
  {
    if (!file1.isFile())
      return false;
    if (!file1.isFile())
      return false;
    
    if (!file1.getName().equals(file2.getName()))
    {
      PrintUtils.printParagraph(0, "Files have different names: " +
          "<"+relPath+file1.getName()+">    -    " +
          "<"+relPath+file2.getName()+">");
      return false;
    }
    
    if (file1.length() != file2.length())
    {
      PrintUtils.printParagraph(0, "Files    <"+relPath+file1.getName()+">   have different sizes : " +
          "<"+file1.length()+">    -    " +
          "<"+file2.length()+">");
    }
    
    File bigFile = file1;
    File smallFile = file2;
    if (file2.length() > file1.length())
    {
      bigFile = file2;
      smallFile = file1;
    }
    
    try
    {
      FileReader bigReader = new FileReader(bigFile);
      FileReader smallReader = new FileReader(smallFile);
      
      BufferedReader bigBuffer = new BufferedReader(bigReader);
      BufferedReader smallBuffer = new BufferedReader(smallReader);
      
      String smallLine;
      String bigLine;
      
      int line = 0;
      while ((smallLine = smallBuffer.readLine()) != null)
      {
        bigLine = bigBuffer.readLine();
        if (!smallLine.equals(bigLine))
        {
          PrintUtils.printParagraph(0, "Files    <"+relPath+file1.getName()+">    differ at line "+line+" : " +
              "\n   " + smallLine + "\n   " + bigLine);
          if (stopOnErr)
            return false;
        }
        line++;
      }
      
    }
    catch (Exception ex)
    {
      PrintUtils.printParagraph(0, "Exception when compating files : " +
          "<"+relPath+file1.getName()+">    -    <"+relPath+file2.getName()+">\n   " + ex);
      return false;
    }
    
    return true;
  }
  
  
  public static boolean compareFilesAsBinary(File file1, File file2, String relPath, boolean stopOnErr)
  {
    int diffs = 0;
    if (!file1.isFile())
      return false;
    if (!file1.isFile())
      return false;
    
    if (!file1.getName().equals(file2.getName()))
    {
      PrintUtils.printParagraph(0, "Files have different names: " +
          "<"+relPath+file1.getName()+">    -    <"+relPath+file2.getName()+">");
      if (stopOnErr)
        return false;
    }
    
    if (file1.length() != file2.length())
    {
      PrintUtils.printParagraph(0, "Files  <"+relPath+file1.getName()+">  have different sizes : " +
          "<"+file1.length()+">    -    <"+file2.length()+">");
    }
    
    File bigFile = file1;
    File smallFile = file2;
    if (file2.length() > file1.length())
    {
      bigFile = file2;
      smallFile = file1;
    }
    
    try
    {
      FileInputStream small_is = new FileInputStream(smallFile);
      FileInputStream big_is = new FileInputStream(bigFile);
      
      int available = small_is.available();
      int off = 0;
      while (available > 0)
      {
        int size = available > 1000 ? 1000 : available;
        byte[] small_b = new byte[size];
        byte[] big_b = new byte[size];
        small_is.read(small_b);
        big_is.read(big_b);
        for (int i = 0; i < size; i++)
        {
          if (small_b[i] != big_b[i])
          {
            diffs++;
            PrintUtils.printParagraph(0, "Files    <"+relPath+file1.getName()+">    differ at position " + (off+i));
            if (stopOnErr)
              return false;
          }
        }
        off+=size;
        available = small_is.available();
      }
    }
    catch (Exception ex)
    {
      PrintUtils.printParagraph(0, "Exception when compating files : " +
          "<"+relPath+file1.getName()+">    -    <"+relPath+file2.getName()+">\n   " + ex);
      return false;
    }
    
    PrintUtils.printParagraph(0, "Diffs count: " + diffs);
    return true;
  }
}
