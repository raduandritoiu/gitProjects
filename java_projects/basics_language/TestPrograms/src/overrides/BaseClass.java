package overrides;

public class BaseClass
{
  public int val;
  public static int staticVal;
  
  static
  {
    staticVal = 5;
  }
  
  public BaseClass(int x)
  {
    val = x;
  }
  
  
  public int computeOne(int a)
  {
    return val + a;
  }
  
  public int computeTwo(int a)
  {
    return val * a;
  }
  
  public int computeOne(int a, int b)
  {
    return val + a + b;
  }
  
  
  public static int ComputeOne(int a)
  {
    return staticVal + a;
  }
  
  public static int ComputeOne(int a, int b)
  {
    return staticVal + a + b;
  }
  
  public static int ComputeTwo(int a)
  {
    return staticVal * a;
  }
}
