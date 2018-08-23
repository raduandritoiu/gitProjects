package scenarios.optimizedQueue.queues;

import scenarios.optimizedQueue.locks.SimpleMutex;
import scenarios.optimizedQueue.packets.Packets.TestPacket;


public class OriginalQueue {
	public int pkt_list_in = 0;
	public int pkt_list_out = 0;
	public final int pkt_lst_size;
	public final TestPacket[] pkt_list;
	
	public final SimpleMutex lock = new SimpleMutex(false);
	public boolean run = true;
	
	
	public OriginalQueue(int size) {
		this (size, false);
	}
	
	public OriginalQueue(int size, boolean init) {
		size = 16;
		pkt_lst_size = size;
		pkt_list = new TestPacket[pkt_lst_size];
		if (init) {
			for (int i = 0; i < pkt_lst_size; i++)
				pkt_list[i] = new TestPacket();
	  }
	}


	
// ===================================================================
	
	//public int advance(int pos) { return (pos+1) & 0xf; }
	public int advance(int pos) { return (pos+1) % pkt_lst_size; }
	public void advanceIn() { pkt_list_in = advance(pkt_list_in); }
	public void advanceOut() { pkt_list_out = advance(pkt_list_out); }
	
	public boolean notFull() { return advance(pkt_list_in) != pkt_list_out; }
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

//===================================================================

	public void insert(String pktInfo) {
		// test if advance
		if (notFull()) {
			// an extra test that place is empty
			if (!pkt_list[pkt_list_in].isEmpty()) {
				System.out.println("Producer: WTF!!!");
			}
			
			// add the packet
			pkt_list[pkt_list_in].setInfo(pktInfo);
			System.out.println("Producer:  produced packet <" + pkt_list[pkt_list_in] + ">   - in: " + pkt_list_in +  " out: " + pkt_list_out);
			// advance
			advanceIn();
		}
		else {
			System.out.println("Producer: list full");
		}
		// signal
		if (shouldSignal())
			signal();
	}
	
	public void consume() {
		while (notEmpty()) {
			// an extra test that place is NOT empty
			if (pkt_list[pkt_list_out].isEmpty()) {
				System.out.println("Consumer: WTF!!!!");
			}
			// actually consume packet
			System.out.println("Consumer: consuming packet <" + pkt_list[pkt_list_out] + ">");
			pkt_list[pkt_list_out].clear();
			// advance
			advanceOut();
		}
		// wait
		lock();
	}
	
	public boolean consumeOne() {
		if (notEmpty()) {
			// an extra test that place is NOT empty
			if (pkt_list[pkt_list_out].isEmpty()) {
				System.out.println("Consumer: WTF!!!!");
			}
			// actually consume packet and remove it
			System.out.println("Consumer: consuming packet <" + pkt_list[pkt_list_out] + ">");
			pkt_list[pkt_list_out].clear();
			
			// advance
			advanceOut();
			return true;
		}
		else {
			System.out.println("Consumer: list is empty, so wait");
			return false;
		}
	}
}
