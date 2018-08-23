package testDispatch.single;

public class TestSingle
{
  public static void test()
  {
    BaseKey baseKey     = new BaseKey(0);
    ExtendKey extendKey = new ExtendKey(0);
    BaseKey poliArg     = new ExtendKey(0);
    
    BaseLock baseLock     = new BaseLock(0);
    ExtendLock extendLock = new ExtendLock(0);
    BaseLock poliLock     = new ExtendLock(0);
    
    
    System.out.println("---- Test Single Dispatch --------");
    String val;
    
    val = baseLock.useKey(baseKey);
    System.out.println("For (base, base) value is : " + val);
    
    val = baseLock.useKey(extendKey);
    System.out.println("For (base, extd) value is : " + val);
    
    val = extendLock.useKey(baseKey);
    System.out.println("For (extd, base) value is : " + val);
    
    val = extendLock.useKey(extendKey);
    System.out.println("For (extd, extd) value is : " + val);
    
    System.out.println("------------------------------------------");
    
    val = baseLock.useKey(poliArg);
    System.out.println("For (base, poli) value is : " + val);
    
    val = extendLock.useKey(poliArg);
    System.out.println("For (extd, poli) value is : " + val);
    
    val = poliLock.useKey(baseKey);
    System.out.println("For (poli, base) value is : " + val);

    val = poliLock.useKey(extendKey);
    System.out.println("For (poli, extd) value is : " + val);

    System.out.println("------------------------------------------");
    
    val = poliLock.useKey(poliArg);
    System.out.println("For (poli, poli) value is : " + val);
    System.out.println("------------------------------------------\n");
  }
}