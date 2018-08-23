package testBytecode;

public class Pyramid
{
  int x, y, z;
  
  public Pyramid(int a)
  {
    x = a;
  }
  
  public int adding(int a, int b)
  {
    y = a;
    z = b;
    int res = 0;
    try 
    {
      res = a * b;
    }
    catch (Exception e)
    {
      res = -1;
    }
    return res;
  }
}
