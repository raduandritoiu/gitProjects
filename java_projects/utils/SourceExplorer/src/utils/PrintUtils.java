package utils;

public class PrintUtils
{
  public static void print(int tab, String str)
  {
    for (int i = 0; i < tab; i++) 
      System.out.print("\t");
    System.out.println(str);
  }
  
  public static void printParagraph(int tab, String str)
  {
    for (int i = 0; i < tab; i++) 
      System.out.print("\t");
    System.out.println(str);
  }
}
