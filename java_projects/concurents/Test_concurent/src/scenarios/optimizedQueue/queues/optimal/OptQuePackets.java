 package scenarios.optimizedQueue.queues.optimal;

import scenarios.optimizedQueue.locks.SimpleMutex;
import scenarios.optimizedQueue.queues.IQueue;
import utils.Utils;


public class OptQuePackets implements IQueue {
	public int pkt_list_in = 0;
	public int pkt_list_out = 0;
	public final int pkt_lst_size;
	public final OptQueProcessPacket[] pkt_list;
  private final OptQueProcessPacket[] processors;
	
	public final SimpleMutex lock = new SimpleMutex(false);
//	private boolean run = true;
	
	
	public OptQuePackets(int size) {
		pkt_lst_size = size;
		pkt_list = new OptQueProcessPacket[pkt_lst_size];
		
    processors = new OptQueProcessPacket[3];
    processors[0] = new OptQueProcessPacketOne(3);
    processors[1] = new OptQueProcessPacketTwo(5);
    processors[2] = new OptQueProcessPacketThree(4);
	}

  public String name() { return "OptQuePackets"; }

// ===================================================================
	//public int advance(int pos) { return (pos+1) & 0xf; }
	public int advance(int pos) { return (pos+1) % pkt_lst_size; }
//===================================================================

	public boolean insert(IInsertElem inPkt) {
		// test if advance
		if (advance(pkt_list_in) != pkt_list_out) {
			// an extra test that place is empty
			if (pkt_list[pkt_list_in] != null) {
				System.out.println(name() + " - producer: WTF!!!");
        Utils.CRASH();
			}
			
			// add the packet
			boolean proc = false;
			int i = 0;
	    for (i = 0 ; i < processors.length && !proc; i++) {
	      proc = processors[i].insert(inPkt);
	    }
	    if (proc) {
	      pkt_list[pkt_list_in] = processors[i-1];
	      // advance
	      pkt_list_in = advance(pkt_list_in);
	    }
	    System.out.println(name() + " - producer:  produced packet <" + pkt_list[pkt_list_in] + ">   - in: " + pkt_list_in +  " out: " + pkt_list_out);
		}
		else {
			System.out.println(name() + " - producer: list full");
			return false;
		}
		
		// signal
		if (pkt_list_in == advance(pkt_list_out)) {
			System.out.println(name() + " - producer: signal");
			lock.unlock();
		}
		return true;
	}
	
	public void consume() {
		while (pkt_list_out != pkt_list_in) {
			// an extra test that place is NOT empty
			if (pkt_list[pkt_list_out] == null) {
				System.out.println(name() + " - consumer: WTF!!!!");
        Utils.CRASH();
			}
			
			// actually consume packet
      ((OptQueProcessPacket)pkt_list[pkt_list_out]).consumeOne();
			System.out.println(name() + " - consumer: consuming packet <" + pkt_list[pkt_list_out] + ">");
			pkt_list[pkt_list_out] = null;
			
			// advance
			pkt_list_out = advance(pkt_list_out);
		}
		// wait
		System.out.println(name() + " - consumer: list is empty, so wait");
		lock.lock();
	}
	
	public IProcessedElem consumeOne() {
	  Utils.CRASH();
	  return null;
	}
	
	
//===================================================================
  public void unlock() {
    lock.unlock();
  }
}
