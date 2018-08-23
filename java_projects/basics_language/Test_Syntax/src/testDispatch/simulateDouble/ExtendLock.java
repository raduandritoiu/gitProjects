package testDispatch.simulateDouble;

public class ExtendLock
    extends BaseLock
{
  public ExtendLock(int start)
  {
    super(start);
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
