package threadPool;

import java.util.concurrent.LinkedBlockingQueue;

import utils.Utils;

public class MyWorkerThread extends Thread
{
  private final LinkedBlockingQueue<MyTask> taksQueue;
  private final int tabId;
  private boolean running;
  
  public MyWorkerThread(LinkedBlockingQueue<MyTask> queue, int tab)
  {
    taksQueue = queue;
    tabId = tab;
    running = true;
  }
  
  public void signalStop()
  {
    running = false;
  }
  
  @Override
  public void run()
  {
    while (running)
    {
      try
      {
        MyTask task = taksQueue.take();
        task.execute();
      }
      catch(InterruptedException intr)
      {
        Utils.PrintTh("Got intrerupted!", tabId);
      }
      catch(Exception exp)
      {
        Utils.PrintTh("Other exception!", tabId);
      }
    }
  }
}
