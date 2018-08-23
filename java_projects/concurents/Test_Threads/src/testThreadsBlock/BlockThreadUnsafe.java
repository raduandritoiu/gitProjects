package testThreadsBlock;

import basicUtils.Utils;
import sun.misc.Unsafe;
import testUnsafe.MyOwnUnsafe;

public class BlockThreadUnsafe extends Thread
{
  private final int cnt;
  private final String name;
  
  public BlockThreadUnsafe(int c, String n)
  {
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
      Utils.Sleep(1000);
      System.out.println(name + ": Block thread: " + i);
      unsafe.park(false, 0L);
      System.out.println(name + ": Was notified thread: " + i);
    }
  }
}
