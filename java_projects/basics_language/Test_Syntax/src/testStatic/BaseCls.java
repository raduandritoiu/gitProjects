package testStatic;

public class BaseCls
{
  private String defBase;
  private int baseStorage;
  private int commStorage;
  
  public BaseCls(int x)
  {
    defBase = "I am the base class";
    baseStorage = x;
    commStorage = 6;
  }
  
  public String def()
  {
    return defBase + ": " + baseStorage;
  }
  
  public void setBase(int x)
  {
    baseStorage = x;
  }
  
  public int getBase()
  {
    return baseStorage;
  }
  
  public void setComm(int x)
  {
    commStorage = x;
  }
  
  public int getComm()
  {
    return commStorage;
  }
  
  public static BaseCls create(int x)
  {
    return new BaseCls(x);
  }
}
