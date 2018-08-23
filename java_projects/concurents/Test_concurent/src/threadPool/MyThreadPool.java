package threadPool;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import utils.Utils;

public class MyThreadPool
{
  private final LinkedBlockingQueue<MyTask> taskQueue;
  private final ConcurrentLinkedQueue<Thread> threadQueue;
  private int thNum;
  private boolean running;

  
  public MyThreadPool(int cap, int th)
  {
    taskQueue = new LinkedBlockingQueue<>(cap);
    threadQueue = new ConcurrentLinkedQueue<>();
    thNum = th;
    running = false;
  }
  
  
  public void start()
  {
    running = true;
  }
  
  public void stop()
  {
    running = false;
  }
  
  
  public void threads(int th)
  {
    running = true;
  }
  
  public int threads()
  {
    return thNum;
  }
  
  
  private void startThreads()
  {
    for (int i = 0; i < thNum; i++)
    {
      MyWorkerThread th = new MyWorkerThread(taskQueue, i);
      
    }
  }
  
  
  
  public LinkedBlockingQueue<MyTask> tasks()
  {
    return taskQueue;
  }
  
  public void addTask(MyTask task)
  {
    while (running)
    {
      try
      {
        taskQueue.put(task);
      }
      catch(InterruptedException intr)
      {
        Utils.PrintTh("Got intrerupted!", 0);
      }
      catch(Exception exp)
      {
        Utils.PrintTh("Other exception!", 0);
      }
    }
  }
  
  public int taskNums()
  {
    return taskQueue.size();
  }
}
