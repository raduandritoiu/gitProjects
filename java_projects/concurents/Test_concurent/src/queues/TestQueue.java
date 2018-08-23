package queues;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestQueue
{
  public static void testNormal()
  {
    NormalQueue que = new NormalQueue();
    
    //
    que.add(5);
    que.add(9);
    que.add(3);
    que.add(2);
    que.add(10);
    
    System.out.println("removed: " + que.remove());
    System.out.println("removed: " + que.remove());
    que.printRevert();
    System.out.println("removed: " + que.remove());
    que.add(8);
    System.out.println("removed: " + que.remove());
    que.add(7);
    que.add(1);
    que.print();
    System.out.println("removed: " + que.remove());
    System.out.println("removed: " + que.remove());
    System.out.println("removed: " + que.remove());
    System.out.println("removed: " + que.remove());
    System.out.println("removed: " + que.remove());
    System.out.println("removed: " + que.remove());
    System.out.println("removed: " + que.remove());
    System.out.println("removed: " + que.remove());
    que.printRevert();
  }
  
  
  public static void test(IQueue que)
  {
    Generator gen = new Generator();
    
    AddingTask[] adders = new AddingTask[3000];
    for (int i = 0; i < 3000; i++)
    {
      int r = 9;
      adders[i] = new AddingTask(que, gen, r);
    }
    
    RemovingTask[] removers = new RemovingTask[2300];
    for (int i = 0; i < 2300; i++)
    {
      int r = i;//Math.random();
      removers[i] = new RemovingTask(que, r);
    }
    
    // start the pool
    ExecutorService pool = Executors.newFixedThreadPool(200);
//    pool.execute(command);
    
  }
}
