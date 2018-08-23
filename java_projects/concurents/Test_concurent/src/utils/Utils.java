package utils;

public class Utils
{
  public static void PrintTh(String str)
  {
    PrintTh(str, 0);
  }
  
  public static void PrintTh(String str, int tab)
  {
    
  }
  
  public static void CRASH() {
    int cc = 10 / 0;
    System.out.println("CRASH : " + cc);
  }
}
