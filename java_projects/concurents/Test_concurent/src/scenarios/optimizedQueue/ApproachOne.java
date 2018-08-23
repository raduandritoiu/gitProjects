package scenarios.optimizedQueue;

public class ApproachOne {
  public static int cnt;
  public static void run() throws Exception {
    OptimalQueue q = new OptimalQueue(10);
    Producer prod = new Producer(q);
    Consumer cons = new Consumer(q);
    
    prod.start();
    cons.start();
    
    prod.join();
    cons.join();
  }
  
  // In this approach: 
  // For Producer it uses 'next' to tests the next-next empty space, and this will 
  // always leave one empty between IN and OUT. 
  // This is useful at the kickstart, when IN and OUT starting at the same position 0.
  // Otherwise they would loop endlessly at the statup.
  // Also is useful so IN and OUT never get equal again.

  
  
  public static class Producer extends Thread {
    public final OptimalQueue q;
    public Producer(OptimalQueue oq) { q = oq; }
    
    public void run() {
      while (true) {
        // test if advance
        int next = (q.pkt_list_in+1) % q.pkt_lst_size;
        if (next != q.pkt_list_out) {
          // produce packets
          if (!q.pkt_list[q.pkt_list_in].msg.equals(OptimalQueue.empty_msg)) {
            System.out.println("Producer: WTF!!!");
          }
          q.pkt_list[q.pkt_list_in].msg = "new packet " + (cnt++);
          System.out.println("Producer:  produced packet <" + q.pkt_list[q.pkt_list_in] + ">");
          
          // advance
          q.pkt_list_in = (q.pkt_list_in+1) % q.pkt_lst_size;
        }
        else {
          System.out.println("Producer: list full");
        }
        
        // signal
        if (q.pkt_list_in == (q.pkt_list_out+1) % q.pkt_lst_size) {
          System.out.println("Producer: signal");
          q.lock.unlock();
        }
      }
    }
  }
  
  
  public static class Consumer extends Thread {
    public final OptimalQueue q;
    public Consumer(OptimalQueue oq) { q = oq; }
    
    public void run() {
      while (true) {
        while (q.pkt_list_out != q.pkt_list_in) {
          // consume packet
          if (q.pkt_list[q.pkt_list_out].msg.equals(OptimalQueue.empty_msg)) {
            System.out.println("Consumer: WTF!!!!");
          }
          System.out.println("Consumer: consuming packet <" + q.pkt_list[q.pkt_list_out] + ">");
          q.pkt_list[q.pkt_list_out].msg = OptimalQueue.empty_msg;
          
          // advance
          q.pkt_list_out = (q.pkt_list_out+1) % q.pkt_lst_size;
        }
        
        System.out.println("Consumer: list is empty");
        // wait
        q.lock.lock();
      }
    }
  }
}
