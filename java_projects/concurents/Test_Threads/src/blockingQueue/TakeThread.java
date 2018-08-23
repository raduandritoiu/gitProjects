package blockingQueue;

import basicUtils.Resource;

public class TakeThread extends Thread
{
  private final IBlockingQue<Resource> que;
  private final String name;
  private final int cnt;
  
  public TakeThread(IBlockingQue<Resource> q, String n)
  {
    this(q, n, 1);
  }
  
  public TakeThread(IBlockingQue<Resource> q, String n, int c)
  {
    que = q;
    name = n;
    setName(n);
    cnt = c;
  }

  public void run()
  {
    System.out.println(name + ": STARTED thread");
    for (int i = 0; i < cnt; i++)
    {
      getFromQue();
    }
  }
  
  private void getFromQue()
  {
    try
    {
      System.out.println(name + ": try take from queue");
      Resource res = que.take();
      int test = res.counter;
      System.out.println(name + ": DONE take from queue");
    }
    catch(InterruptedException ex)
    {
      System.out.println("(FUCK - " + name + ": woke up from Take)");
    }
  }
}
