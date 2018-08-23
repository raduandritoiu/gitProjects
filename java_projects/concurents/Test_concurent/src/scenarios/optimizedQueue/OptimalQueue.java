package scenarios.optimizedQueue;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class OptimalQueue {
	public static final String empty_msg = "empty";

	public int pkt_list_in = 0;
	public int pkt_list_out = 0;
  public int pkt_lst_size;
	public final Pkt[] pkt_list;
	
	public QueueLock lock = new QueueLock(false);
	public boolean run = true;
	
	
	public OptimalQueue(int size) {
	  size = 16;
	  pkt_lst_size = size;
	  pkt_list = new Pkt[pkt_lst_size];
		for (int i = 0; i < pkt_lst_size; i++)
			pkt_list[i] = new Pkt(empty_msg);
	}

//public int advance(int pos) { return (pos+1) & 0xf; }
  public int advance(int pos) { return (pos+1) % pkt_lst_size; }
  public void advanceIn() { pkt_list_in = advance(pkt_list_in); }
  public void advanceOut() { pkt_list_out = advance(pkt_list_out); }
	
  public boolean isFull() { return advance(pkt_list_in) == pkt_list_out; }
  public boolean notFull() { return advance(pkt_list_in) != pkt_list_out; }
  
  public boolean isEmpty() { return pkt_list_out == pkt_list_in; }
  public boolean notEmpty() { return pkt_list_out != pkt_list_in; }
  
  public boolean shouldSignal() { return pkt_list_in == advance(pkt_list_out); }
  
  public void lock() {
    System.out.println("Consumer: list is empty, so wait");
    lock.lock();
  }
  public void signal() {
    System.out.println("Producer: signal");
    lock.unlock();
  }
  public void signalAbs() {
    if (shouldSignal()) {
      System.out.println("Producer: signal");
      lock.unlock();
    }
  }

  public void produce() {
    
  }
  public void consume() {
    
  }
  
// ===================================================================
  
  public static interface IPkt {}

  public static class Pkt implements IPkt {
		public String msg;
		public Pkt() {}
		public Pkt(String nMsg) { msg = nMsg; }
		public String toString() { return msg; }
	}
	
	
	public class QueueLock extends AbstractQueuedSynchronizer {
    private static final long serialVersionUID = 4982264981922014374L;

    protected QueueLock(boolean open) { setState(open ? 1 : 0); }
    protected boolean tryAcquire(int acquires) { return compareAndSetState(1, 0); }
    protected boolean tryRelease(int releases) { return compareAndSetState(0, 1); }
    
    public void lock() { acquire(1); }
    public void unlock() { release(1); }
    public boolean isOpen() { return (getState() == 1); }
	}
}
