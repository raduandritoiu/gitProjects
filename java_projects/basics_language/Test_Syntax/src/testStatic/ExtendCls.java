package testStatic;

public class ExtendCls
    extends BaseCls
{
  private String defExt;
  private int extStorage;
  
  public ExtendCls(int x)
  {
    super(101);
    defExt = "This is the extended version";
    setComm(103);
    extStorage = x;
  }
  
  @Override
  public String def()
  {
    return defExt + ": " + extStorage;
  }
  
  public void setExt(int x)
  {
    extStorage = x;
  }
  
  public int getExt()
  {
    return extStorage;
  }
  
  public static BaseCls create(int x)
  {
    return new ExtendCls(x);
  }
}
