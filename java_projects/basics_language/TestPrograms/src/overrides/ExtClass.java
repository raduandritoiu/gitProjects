package overrides;

public class ExtClass extends BaseClass
{
  public static int staticVal;
  
  static
  {
    staticVal = 10;
  }

  
  public ExtClass(int x)
  {
    super(x);
  }

  
  public static int ComputeTwo(int a)
  {
    return staticVal / a;
  }
}
