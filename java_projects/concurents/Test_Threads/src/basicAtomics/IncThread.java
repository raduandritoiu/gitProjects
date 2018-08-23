package basicAtomics;

import basicUtils.Resource;

public class IncThread extends Thread
{
  private int cnt;
  private String name;
  private Resource res;
  
  public IncThread(Resource r, int c, String n)
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
      res.counter = res.counter + 1;
    }
    System.out.println(name + ": Stoped thread");
  }
}
