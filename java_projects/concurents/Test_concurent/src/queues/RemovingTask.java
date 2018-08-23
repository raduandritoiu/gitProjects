package queues;

public class RemovingTask implements Runnable
{
  public IQueue que;
  public int num;
  
  public RemovingTask(IQueue q, int n)
  {
    que = q;
    num = n;
  }
  
  public void run()
  {
    for (int i = 0; i < num; i++)
    {
      int val = que.remove();
      System.out.println("                                  Removing: " + val + " !");
    }
  }
}
