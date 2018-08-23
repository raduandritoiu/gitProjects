package testThreadsBlock;

import basicUtils.Utils;

public class NotifyThread extends Thread
{
  private final Thread th;
  private final int cnt;
  private final String name;
  
  public NotifyThread(Thread t, int c, String n)
  {
    th = t;
    cnt = c;
    name = n;
    setName(n);
  }
  
  @Override
  public void run()
  {
    for (int i = 0; i < cnt; i++)
    {
//      Utils.Sleep(1000);
      System.out.println(name + ": Notify monitor: " + i);
      th.resume();
    }
  }
}
