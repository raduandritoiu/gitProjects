package basicObject;

import basicUtils.MonitorFromObj;
import basicUtils.Utils;

public class BlockingThread extends Thread
{
  private final MonitorFromObj monitor;
  private final String name;
  
  public BlockingThread(MonitorFromObj mon, String n)
  {
    monitor = mon;
    name = n;
    setName(n);
  }
  
  public String name()
  {
    return name;
  }

  public void run()
  {
    System.out.println(name + ": Started thread");
    Utils.SleepRand(2000);
    
    System.out.println(name + ": Try to enter monitor");
    synchronized (monitor)
    {
      try
      {
        System.out.println(name + ": Block thread");
        monitor.wait();
      }
      catch (InterruptedException ex)
      {
        System.out.println(name + ": (FUCK - woke up from Wait)");
      }
      System.out.println(name + ": Was notified thread");
    }
    System.out.println(name + ": Leave Monitor");
  }
}
