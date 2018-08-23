package testThreadsBlock;

import basicUtils.Utils;

public class BlockThread extends Thread
{
  private final int cnt;
  private final String name;
  
  public BlockThread(int c, String n)
  {
    cnt = c;
    name = n;
    setName(n);
  }
  
  @Override
  public void run()
  {
    for (int i = 0; i < cnt; i++)
    {
      Utils.Sleep(1000);
      System.out.println(name + ": Block thread: " + i);
      suspend();
      System.out.println(name + ": Was notified thread: " + i);
    }
  }
}
