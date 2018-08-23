package testThreadsBlock;

import basicUtils.Utils;
import sun.misc.Unsafe;
import testUnsafe.MyOwnUnsafe;

public class NotifyThreadUnsafe extends Thread
{
  private final Thread th;
  private final int cnt;
  private final String name;
  
  public NotifyThreadUnsafe(Thread t, int c, String n)
  {
    th = t;
    cnt = c;
    name = n;
    setName(n);
  }
  
  @Override
  public void run()
  {
    Unsafe unsafe = MyOwnUnsafe.getInstance();
    
    for (int i = 0; i < cnt; i++)
    {
//      Utils.Sleep(1000);
      System.out.println(name + ": Notify monitor: " + i);
      unsafe.unpark(th);
    }
  }
}
