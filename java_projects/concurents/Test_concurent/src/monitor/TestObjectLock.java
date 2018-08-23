package monitor;

import java.util.concurrent.atomic.AtomicBoolean;

public class TestObjectLock
{
  
  public static void test() throws Exception
  {
    MonitorLock lock = new MonitorLock();
    
    int wtlen = 10;
    WaitingThread[] wt = new WaitingThread[wtlen];
    for (int i = 0; i < wtlen; i++)
      wt[i] = new WaitingThread(lock, "worker-" + i);
    
    int sglen = 3;
    SignalThread[] sg = new SignalThread[sglen];
    for (int i = 0; i < sglen; i++)
      sg[i] = new SignalThread(lock, "signal-" + i);
    
    SignalAllThread sga = new SignalAllThread(lock, "signal_all-0");
    
    // start the waiters
    for (int i = 0; i < wtlen; i++)
      wt[i].start();
    
    Thread.sleep(1000);
    
    AtomicBoolean ar;
    for (int i = 0; i < sglen; i++)
      sg[i].start();

//    sga.start();
    
    println("END!");
  }

  public static void println(String str)
  {
    System.out.println(Thread.currentThread().getName() + ": " + str);
  }
  
  
  private static class WaitingThread extends Thread
  {
    public MonitorLock lock;
    public WaitingThread(MonitorLock nLock, String name)
    {
      lock = nLock;
      setName(name);
    }
    
    public void run()
    {
      try 
      {
        println("Start working Task!");
        synchronized (lock)
        {
          println("working at something (entered monitor)");
          Thread.sleep(1000);
          println("wait for lock");
          lock.wait();
          println("was notified");
          Thread.sleep(1000);
          println("finish working on something (exit monitor)");
        }
        println("End Task!");
      }
      catch(Exception ex) 
      {
        println("muie");
      }
    }
  }
  
  
  private static class SignalThread extends Thread
  {
    public MonitorLock lock;
    public SignalThread(MonitorLock nLock, String name)
    {
      lock = nLock;
      setName(name);
    }
    
    public void run()
    {
      try
      {
        println("Start Signal Task!");
        synchronized (lock)
        {
          println("start to do stuff (enter the monitor)");
          Thread.sleep(2000);
          println("signal someone");
          lock.notify();
          println("just signaled someone");
          Thread.sleep(2000);
          println("finish doing stuff (exit the monitor)");
        }
        println("End Signal Task!");
      }
      catch(Exception ex) 
      {
        println("muie");
      }
    }
  }
  
  
  private static class SignalAllThread extends Thread
  {
    public MonitorLock lock;
    public SignalAllThread(MonitorLock nLock, String name)
    {
      lock = nLock;
      setName(name);
    }
    
    public void run()
    {
      System.out.println("Start Signal All Task!");
      try
      {
        println("Start Signal All Task!");
        synchronized (lock)
        {
          println("start to do stuff (enter the monitor)");
          Thread.sleep(2000);
          println("signal all of them");
          lock.notifyAll();
          println("just signaled all of them");
          Thread.sleep(2000);
          println("finish doing stuff (exit the monitor)");
        }
        println("End Signal All Task!");
      }
      catch(Exception ex) 
      {
        println("muie");
      }
    }
  }
}
