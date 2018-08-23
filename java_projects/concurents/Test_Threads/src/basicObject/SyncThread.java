package basicObject;

import basicUtils.MonitorFromObj;
import basicUtils.Utils;


// thread synchronized at Object implicit locks
public class SyncThread
    extends Thread
{
  private final MonitorFromObj monitor;
  private final String name;
  
  public SyncThread(MonitorFromObj mon, String n)
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
    Utils.SleepRand(1000);
    
    System.out.println(name + ": Try to enter monitor");
    synchronized (monitor)
    {
      System.out.println(name + ": Enter monitor");
      Utils.SleepRand(10000);
      System.out.println(name + ": Leave monitor");
    }
    System.out.println(name + ": Left monitor");
  }
}
