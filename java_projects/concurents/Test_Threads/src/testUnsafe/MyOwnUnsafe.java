package testUnsafe;

import java.lang.reflect.Constructor;

import sun.misc.Unsafe;

public class MyOwnUnsafe
{
  public MyOwnUnsafe()
  {
  }
  
  private static Unsafe _inst;
  
  public static Unsafe getInstance()
  {
    if (_inst == null)
      createInstance();
    return _inst;
  }
  
  public static boolean createInstance()
  {
    try
    {
      Constructor<Unsafe> unsafeConstructor = Unsafe.class.getDeclaredConstructor();  
      unsafeConstructor.setAccessible(true);
      _inst = unsafeConstructor.newInstance();
      return true;
    }
    catch (Exception ex)
    {
      return false;
    }
  }
}
