package testThreadsBlock;

import basicUtils.Utils;

public class BlockThreadObj extends Thread
{
  private final Object lock;
  private final int cnt;
  private final String name;
  
  public BlockThreadObj(Object l, int c, String n)
  {
    lock = l;
    cnt = c;
    name = n;
    setName(n);
  }
  
  @Override
  public void run()
  {
//    System.out.println(name + ": Started thread with: " + cnt);

    for (int i = 0; i < cnt; i++)
    {
//      System.out.println(name + ": Try to enter monitor: " + i);
      synchronized (lock)
      {
        try
        {
          System.out.println(name + ": Block thread: " + i);
          lock.wait();
        }
        catch (InterruptedException ex)
        {
          System.out.println(name + ": (FUCK - woke up from Wait)");
        }
        System.out.println(name + ": Was notified thread: " + i);
      }
//      System.out.println(name + ": Left Monitor: " + i);
    }
    
//    System.out.println(name + ": End thread!");
  }
}
