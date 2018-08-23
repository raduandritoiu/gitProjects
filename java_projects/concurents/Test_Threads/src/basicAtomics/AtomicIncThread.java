package basicAtomics;

import java.util.concurrent.atomic.AtomicInteger;


public class AtomicIncThread extends Thread
{
  private int cnt;
  private String name;
  private AtomicInteger res;
  
  
  public AtomicIncThread(AtomicInteger r, int c, String n)
  {
    res = r;
    cnt = c;
    name = n;
    setName(n);
  }
  
  public void run()
  {
    System.out.println(name + ": Started thread");
    for (int i = 0; i < cnt; i++)
    {
//      res.incrementAndGet(); // works
      
//      res.addAndGet(1); // works
      
      for (;;) // works - busy wait
      {
        int ex = res.get();
        int up = ex + 1;
        if (res.compareAndSet(ex, up))
        {
          break;
        }
      }
    }
    System.out.println(name + ": Stoped thread");
  }
}
