package testUnsafe.sizeof;

public class ExtendA extends BaseA
{
  public int c;
  private int d;
  
  public ExtendA()
  {
    d = 233;
  }
  
  public int getD()
  {
    return d;
  }
  
  public float sum()
  {
    return super.sum() + c + d;
  }
  
  private int internalExtend()
  {
    return d - c;
  }
}
