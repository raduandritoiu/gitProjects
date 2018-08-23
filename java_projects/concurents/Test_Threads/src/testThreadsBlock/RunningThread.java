package testThreadsBlock;

public class RunningThread extends Thread
{
  private final int cnt1;
  private final int cnt2;
  private final String name;

  public RunningThread(int c1, int c2, String n)
  {
    cnt1 = c1;
    cnt2 = c2;
    name = n;
    setName(n);
  }
  
  
  @Override
  public void run()
  {
    System.out.println(name + ": Start thread");
    for (int i = 0; i < cnt1; i++)
    {
      for (int j = 0; j < cnt2; j++)
      {
        System.out.print("*");
        int x = i * cnt2 + j;
        if ((x % 60) == 0)
        {
          System.out.print("\n");
        }
      }
    }
    System.out.println(name + ": End thread");
  }
}
