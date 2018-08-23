package testDispatch.simulateDouble;

public class BaseLock
{
  public BaseLock(int start)
  {
  }
  
  public String useKey(ExtendKey arg)
  {
    return arg.usedByLock(this);
  }
  
  public String useKey(BaseKey arg)
  {
    return arg.usedByLock(this);
  }
}