package testThreadsBlock;

import basicUtils.Utils;

public class NotifyThreadObj extends Thread
{
  private final Object lock;
  private final int cnt;
  private final String name;
  
  public NotifyThreadObj(Object l, int c, String n)
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
      Utils.Sleep(1000);
//      System.out.println(name + ": Try to enter monitor: " + i);
      synchronized (lock)
      {
        System.out.println(name + ": Notify monitor: " + i);
        lock.notify();
//        System.out.println(name + ": Leave monitor: " + i);
      }
//      System.out.println(name + ": Left monitor: " + i);
    }
    
//    System.out.println(name + ": End thread!");
  }
}
