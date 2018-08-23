package explore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;

import utils.PrintUtils;

public class Explorer
{
  public static void exploreDirectory(int level, String prefix, File crtFile)
  {
    if (crtFile.isFile())
      return;
    PrintUtils.print(level, prefix);
    
    level ++;
    File[] files = crtFile.listFiles();
    
    // files
    for (int i = 0; i < files.length; i++)
    {
      File file = files[i];
      if (file.isFile())
        exploreFile(level, file);
    }
    
    // files
    for (int i = 0; i < files.length; i++)
    {
      File file = files[i];
      if (file.isDirectory())
        exploreDirectory (level, prefix + "." + file.getName(), file);
    }
  }
  
  private static void exploreFile(int level, File file)
  {
    if (!file.isFile())
      return;
    
    String str = file.getName() + " - ";
    try
    {
      FileReader reader = new FileReader(file);
      BufferedReader bf = new BufferedReader(reader);
      boolean start = true;
      while (start)
      {
        String line = bf.readLine();
        if (line.indexOf("public enum ") == 0)
        {
          bf.readLine();
          for (int i = 0; i< 3; i++)
          {
            String l1 = bf.readLine();
            StringTokenizer tok = new StringTokenizer(l1, ", \t");
            if (tok.hasMoreTokens())
            {
              str = str + tok.nextToken() + ", ";
            }
          }          
          start = false;
          break;
        }
      }
    }
    catch (Exception ex)
    {
    }
    PrintUtils.print(level, str);
  }
}
