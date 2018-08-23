package scenarios.optimizedQueue.tests;

import java.util.Random;

import scenarios.optimizedQueue.packets.Packets.InputPacket;
import scenarios.optimizedQueue.queues.IQueue;


public class TestUtils {
  public static int cnt;
  public static boolean run = true;
  
  
  public static void startTest(IQueue q, Thread prod) throws Exception {
    Thread cons = new Consumer(q);
    startTest(q, prod, cons);
  }
  
  public static void startTest(IQueue q, Thread prod, Thread cons) throws Exception {
    prod.start();
    cons.start();
    
    try { Thread.sleep(10); }
    catch (Exception ex) {}
    run = false;
    
    q.unlock();
    prod.join();
    cons.join();
  }
  
  
  public static class Consumer extends Thread {
    public final IQueue q;
    public Consumer(IQueue nq) { q = nq; }
    
    public void run() {
      while (run) {
        q.consume();
      }
    }
  }
  
  
  public static class SingleProducer extends Thread {
    public final IQueue q;
    public SingleProducer(IQueue oq) { q = oq; }
    
    public void run() {
      while (TestUtils.run) {
        InputPacket newPkg = new InputPacket("new packet " + (TestUtils.cnt++));
        q.insert(newPkg);
      }
    }
  }
  
  
  public static class MultipleProducer extends Thread {
    private final IQueue q;
    private final boolean rd;
    public MultipleProducer(IQueue oq, boolean rand) { q = oq; rd = rand; }
    
    public void run() {
      int x;
      InputPacket pkt;
      Random rand = new Random();
      int in[] = {1, 1, 2, 2, 3, 0, 0, 3, 1, 1, 0, 1, 3, 0, 2, 2, 3, 3, 0, 0, 3, 2, 2, 2};
      for (int i = 0; (i < in.length || rd) && TestUtils.run; i++) {
        if (rd) 
          x = rand.nextInt(4);
        else 
          x = in[i];
        
        if (x == 1) pkt = new InputPacket("new packet One " + (TestUtils.cnt++), x);
        else if (x == 2) pkt = new InputPacket("new packet Two " + (TestUtils.cnt++), x);
        else if (x == 3) pkt = new InputPacket("new packet Three " + (TestUtils.cnt++), x);
        else pkt = new InputPacket("new packet " + (TestUtils.cnt++));
        
        q.insert(pkt);
      }
    }
  }
}
