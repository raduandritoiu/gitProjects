 package scenarios.optimizedQueue.queues.actual;

import scenarios.optimizedQueue.locks.SimpleMutex;
import scenarios.optimizedQueue.queues.IQueue;
import utils.Utils;


public class PacketsQueue implements IQueue {
	public int pkt_list_in = 0;
	public int pkt_list_out = 0;
	public final int pkt_lst_size;
	public final int[] pkt_list;
	
	public final SimpleMutex lock = new SimpleMutex(false);
	public boolean run = true;
	
  private final IPacketProcessor[] pktProc;
	
	public PacketsQueue(int size) {
		pkt_lst_size = size;
    pkt_list = new int[pkt_lst_size];
    for (int i = 0; i < pkt_lst_size; i++)
      pkt_list[i] = -1;
		
    pktProc = new IPacketProcessor[3];
    pktProc[0] = new ProcessPacketOne(3);
    pktProc[1] = new ProcessPacketTwo(5);
    pktProc[2] = new ProcessPacketThree(4);
	}

  public String name() { return "PacketsQueue"; }

// ===================================================================
//	private int advance(int pos) { return (pos+1) & 0xf; }
	private int advance(int pos) { return (pos+1) % pkt_lst_size; }
//===================================================================
	
	
	public boolean insert(IInsertElem inPkt) {
		// test if advance
		if (advance(pkt_list_in) != pkt_list_out) {
			// an extra test that place is empty
			if (pkt_list[pkt_list_in] != -1) {
				System.out.println(name() + " - producer: WTF!!!");
        Utils.CRASH();
			}
			
			// add the packet
			boolean accept = false;
			int i = 0;
	    for (i = 0 ; i < pktProc.length && !accept; i++) {
	      accept = pktProc[i].acceptPacket(inPkt);
	    }
	    if (accept) {
	      pkt_list[pkt_list_in] = i-1;
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
			if (pkt_list[pkt_list_out] == -1) {
				System.out.println(name() + " - consumer: WTF!!!!");
        Utils.CRASH();
			}
			
			// actually consume packet
			System.out.println(name() + " - consumer: consuming packet <" + pkt_list[pkt_list_out] + ">");
			pktProc[pkt_list[pkt_list_out]].processPacket();
			pkt_list[pkt_list_out] = -1;
			
			// advance
			pkt_list_out = advance(pkt_list_out);
		}
		// wait
		System.out.println(name() + " - consumer: list is empty, so wait");
		lock.lock();
	}
	
	
	public IProcessedElem consumeOne() { Utils.CRASH(); return null; }
	

//===================================================================
  public void unlock() {
    lock.unlock();
  }
}
