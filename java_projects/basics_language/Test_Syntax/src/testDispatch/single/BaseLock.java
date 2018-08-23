package testDispatch.single;

public class BaseLock
{
  public BaseLock(int start)
  {
  }
  
  public String useKey(ExtendKey arg)
  {
    return "(base, extd)";
  }
  
  public String useKey(BaseKey arg)
  {
    return "(base, base)";
  }
}