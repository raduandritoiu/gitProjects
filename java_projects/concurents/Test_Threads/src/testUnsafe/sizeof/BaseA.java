package testUnsafe.sizeof;

public class BaseA
{
  public static int STAT_A;
  
  public float[] arr;
  private long a;
  public long b;
  
  public BaseA()
  {
    a = 9;
    arr = new float[30];
  }
  
  public float getA()
  {
    return a;
  }
  
  public float sum()
  {
    return a + b;
  }
  
  private float internalBase()
  {
    return b - a;
  }
}
