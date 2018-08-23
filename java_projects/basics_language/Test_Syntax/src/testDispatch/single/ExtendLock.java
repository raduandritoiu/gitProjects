package testDispatch.single;

public class ExtendLock
    extends BaseLock
{
  public ExtendLock(int start)
  {
    super(start);
  }
  
  public String useKey(ExtendKey arg)
  {
    return "(extd, extd)";
  }
  
  public String useKey(BaseKey arg)
  {
    return "(extd, base)";
  }
}
