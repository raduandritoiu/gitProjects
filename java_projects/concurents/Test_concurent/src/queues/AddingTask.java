package queues;

public class AddingTask implements Runnable
{
  public Generator gen;
  public IQueue que;
  public int num;
  
  public AddingTask(IQueue q, Generator g, int n)
  {
    que = q;
    gen = g;
    num = n;
  }
  
  public void run()
  {
    int val = 0;
    for (int i = 0; i < num; i++)
    {
      val = gen.get();
      System.out.println("Adding: " + val + " !");
      que.add(val);
    }
  }
}
