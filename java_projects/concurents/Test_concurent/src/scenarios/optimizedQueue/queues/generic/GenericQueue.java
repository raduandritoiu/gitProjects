package scenarios.optimizedQueue.queues.generic;

import scenarios.optimizedQueue.locks.SimpleMutex;
import scenarios.optimizedQueue.queues.IQueue;


public class GenericQueue<QE extends IQueue.IQueueElem> implements IQueue {
	public int pkt_list_in = 0;
	public int pkt_list_out = 0;
	public final int pkt_lst_size;
	public QE[] pkt_list;
	
	public final SimpleMutex lock = new SimpleMutex(false);
//	private boolean run = true;
	
	
	public GenericQueue(int size) {
		pkt_lst_size = size;
		initQueue();
	}

	 public String name() { return "GenericQueue"; }

//===================================================================
	protected int advance(int pos) { return (pos+1) % pkt_lst_size; }
	protected void advanceIn() { pkt_list_in = advance(pkt_list_in); }
	protected void advanceOut() { pkt_list_out = advance(pkt_list_out); }
	
	protected boolean notFull() { return advance(pkt_list_in) != pkt_list_out; }
	protected boolean notEmpty() { return pkt_list_out != pkt_list_in; }
	
	protected void lock() {
		System.out.println(name() + " - consumer: list is empty, so wait");
		lock.lock();
	}
	protected void signal() {
		if (pkt_list_in == advance(pkt_list_out)) {
			System.out.println(name() + " - producer: signal");
			lock.unlock();
		}
	}

//===================================================================
	protected void initQueue() {}

	protected void testInsert() {}
	
	protected void testConsume() {}
	
	protected boolean processInsert(IInsertElem inPkt) { return true; }
	
	protected IProcessedElem processConsume() { return null; }
	
//===================================================================
	public boolean insert(IInsertElem inPkt) {
		boolean proc;
		// test if advance
		if (notFull()) {
			// an extra test that place is empty
			testInsert();
			// add the packet
			proc = processInsert(inPkt);
			if (proc) {
				// advance
				advanceIn();
			}
		}
		else {
			System.out.println(name() + " - producer: list full");
			proc = false;
		}
		// signal
		signal();
		return proc;
	}
	
	public void consume() {
		while (notEmpty()) {
			// an extra test that place is NOT empty
			testConsume();
			// actually consume packet and remove it - do not keep the processed result
			processConsume();
			// advance
			advanceOut();
		}
		// wait
		lock();
	}
	
	public IProcessedElem consumeOne() {
		if (notEmpty()) {
			// an extra test that place is NOT empty
			testConsume();
			// actually consume packet
			IProcessedElem consumed = processConsume();
			// advance
			advanceOut();
			return consumed;
		}
		else {
			System.out.println(name() + " - consumer: list is empty, so wait");
			return null;
		}
	}
	
//===================================================================
	public void unlock() {
	  lock.unlock();
	}
}
