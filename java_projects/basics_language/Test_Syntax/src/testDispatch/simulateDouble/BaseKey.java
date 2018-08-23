package testDispatch.simulateDouble;

public class BaseKey
{
  public BaseKey(int start)
  {
  }
  
  public String usedByLock(BaseLock lock)
  {
    return "(base, base)";
  }
  
  public String usedByLock(ExtendLock lock)
  {
    return "(extd, base)";
  }
}
