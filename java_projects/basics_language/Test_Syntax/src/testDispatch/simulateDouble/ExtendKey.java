package testDispatch.simulateDouble;

public class ExtendKey
    extends BaseKey
{
  public ExtendKey(int start)
  {
    super(start);
  }
  
  public String usedByLock(BaseLock lock)
  {
    return "(base, extd)";
  }
  
  public String usedByLock(ExtendLock lock)
  {
    return "(extd, extd)";
  }
}
