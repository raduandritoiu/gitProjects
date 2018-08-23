package blockingQueue;

import basicUtils.Resource;
import basicUtils.Utils;


public class Test
{
  public static void testStandardQueue()
  {
    IBlockingQue<Resource> que = new StdSyncQue<>(100010);
    secondQueueTest(que, 1000, 100);
  }
  
  
  public static void testCustomeQueue()
  {
    IBlockingQue<Resource> que = new StdSyncQue<>(5);
    firstQueueTest(que);
  }
  
  
  
  
  public static void firstQueueTest(IBlockingQue<Resource> que)
  {
    try
    {
      que.put(new Resource());
      que.put(new Resource());
    }
    catch(InterruptedException ex)
    {
      System.out.println("(FUCK - woke up from Take)");
    }
    int alive = 0;
    
    TakeThread t1 = new TakeThread(que, "        <Take 1>");
    TakeThread t2 = new TakeThread(que, "        <Take 2>");
    TakeThread t3 = new TakeThread(que, "        <Take 3>");
    TakeThread t4 = new TakeThread(que, "        <Take 4>");
    TakeThread t5 = new TakeThread(que, "        <Take 5>");
    TakeThread t6 = new TakeThread(que, "        <Take 6>");
    TakeThread t7 = new TakeThread(que, "        <Take 7>");
    
    PutThread p1 = new PutThread(que, "                              <Put 1>");
    PutThread p2 = new PutThread(que, "                              <Put 2>");
    PutThread p3 = new PutThread(que, "                              <Put 3>");
    PutThread p4 = new PutThread(que, "                              <Put 4>");
    PutThread p5 = new PutThread(que, "                              <Put 5>");
    PutThread p6 = new PutThread(que, "                              <Put 6>");
    PutThread p7 = new PutThread(que, "                              <Put 7>");
    PutThread p8 = new PutThread(que, "                              <Put 8>");
    PutThread p9 = new PutThread(que, "                              <Put 9>");
    
    System.out.println("Que starts with: " + que.size());
    
    t1.start();
    t2.start();
    t3.start();
    t4.start();
    
    Utils.Sleep(1000);
    alive = 0;
    if (t1.isAlive()) alive ++;
    if (t2.isAlive()) alive ++;
    if (t3.isAlive()) alive ++;
    if (t4.isAlive()) alive ++;
    System.out.println("Some takers should be blocked: " + alive);
    System.out.println("Que has: " + que.size());
    
    Utils.Sleep(1000);
    
    p1.start();
    p2.start();
    p3.start();
    
    Utils.Sleep(1000);
    alive = 0;
    if (t1.isAlive()) alive ++;
    if (t2.isAlive()) alive ++;
    if (t3.isAlive()) alive ++;
    if (t4.isAlive()) alive ++;
    if (p1.isAlive()) alive ++;
    if (p2.isAlive()) alive ++;
    if (p3.isAlive()) alive ++;
    System.out.println("No one is blocked: " + alive);
    System.out.println("Que has: " + que.size());
    Utils.Sleep(1000);
    
    p4.start();
    p5.start();
    p6.start();
    p7.start();
    p8.start();
    p9.start();
    
    Utils.Sleep(1000);
    alive = 0;
    if (t1.isAlive()) alive ++;
    if (t2.isAlive()) alive ++;
    if (t3.isAlive()) alive ++;
    if (t4.isAlive()) alive ++;
    if (p1.isAlive()) alive ++;
    if (p2.isAlive()) alive ++;
    if (p3.isAlive()) alive ++;
    if (p4.isAlive()) alive ++;
    if (p5.isAlive()) alive ++;
    if (p6.isAlive()) alive ++;
    if (p7.isAlive()) alive ++;
    if (p8.isAlive()) alive ++;
    if (p9.isAlive()) alive ++;
    System.out.println("Some putters should be blocked: " + alive);
    System.out.println("Que has: " + que.size());
    Utils.Sleep(1000);
    
    t5.start();
    t6.start();
    t7.start();
    
    Utils.Sleep(10000);
    alive = 0;
    if (t1.isAlive()) alive ++;
    if (t2.isAlive()) alive ++;
    if (t3.isAlive()) alive ++;
    if (t4.isAlive()) alive ++;
    if (t5.isAlive()) alive ++;
    if (t6.isAlive()) alive ++;
    if (t7.isAlive()) alive ++;
    if (p1.isAlive()) alive ++;
    if (p2.isAlive()) alive ++;
    if (p3.isAlive()) alive ++;
    if (p4.isAlive()) alive ++;
    if (p5.isAlive()) alive ++;
    if (p6.isAlive()) alive ++;
    if (p7.isAlive()) alive ++;
    if (p8.isAlive()) alive ++;
    if (p9.isAlive()) alive ++;
    System.out.println("Everyone should be finished: " + alive);
    System.out.println("Que has " + que.size());
  }
  
  
  public static void secondQueueTest(IBlockingQue<Resource> que, int thCnt, int msgCnt)
  {
    PutThread[] ths = new PutThread[thCnt];
    for (int i=0; i < thCnt; i++)
    {
      ths[i] = new PutThread(que, "put", msgCnt);
    }
    
    int init = que.size();
    System.out.println("Que has " + init);
    
    for (int i=0; i < thCnt; i++)
    {
      ths[i].start();
    }
    
    Utils.Sleep(10000);
    init = init + thCnt * msgCnt;
    System.out.println("Que has " + que.size());
    System.out.println("Should of had: " + init);
  }
  
  
  public static void thirdQueueTest(IBlockingQue<Resource> que, int thCnt, int msgCnt)
  {
    TakeThread[] ths = new TakeThread[thCnt];
    for (int i=0; i < thCnt; i++)
    {
      ths[i] = new TakeThread(que, "take", msgCnt);
      for (int j = 0; j < msgCnt; j++)
      {
        try
        {
          que.put(new Resource());
        }
        catch(InterruptedException ex)
        {
          System.out.println("(FUCK - woke up from Put)");
        }
      }
    }
    try
    {
      que.put(new Resource());
      que.put(new Resource());
    }
    catch(InterruptedException ex)
    {
      System.out.println("(FUCK - woke up from Put)");
    }
    
    int init = que.size();
    System.out.println("Que has " + init);
    
    for (int i=0; i < thCnt; i++)
    {
      ths[i].start();
    }
    
    Utils.Sleep(10000);
    init = init - thCnt * msgCnt;
    System.out.println("Que has " + que.size());
    System.out.println("Should've had: " + init);
  }
}
