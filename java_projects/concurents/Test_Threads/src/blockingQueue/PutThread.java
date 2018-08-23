package blockingQueue;

import basicUtils.Resource;


public class PutThread extends Thread
{
  private final IBlockingQue<Resource> que;
  private final String name;
  private final int cnt;
  
  public PutThread(IBlockingQue<Resource> q, String n)
  {
    this(q, n, 1);
  }
  
  public PutThread(IBlockingQue<Resource> q, String n, int c)
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
      putInQue();
    }
  }
  
  private void putInQue()
  {
    try
    {
      System.out.println(name + ": try put in queue");
      que.put(new Resource());
      System.out.println(name + ": DONE put in queue");
    }
    catch(InterruptedException ex)
    {
      System.out.println("(FUCK - " + name + ": woke up from Put)");
    }
  }
}
