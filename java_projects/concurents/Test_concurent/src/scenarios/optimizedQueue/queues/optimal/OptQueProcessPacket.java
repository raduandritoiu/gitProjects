 package scenarios.optimizedQueue.queues.optimal;

import scenarios.optimizedQueue.packets.Packets.InputPacket;
import scenarios.optimizedQueue.packets.Packets.OutputPacket;
import scenarios.optimizedQueue.packets.Packets.TestPacket;
import scenarios.optimizedQueue.queues.IQueue;
import utils.Utils;


public class OptQueProcessPacket implements IQueue {
	public int pkt_list_in = 0;
	public int pkt_list_out = 0;
	public final int pkt_lst_size;
	public final TestPacket[] pkt_list;
	
	
	public OptQueProcessPacket(int size) {
		pkt_lst_size = size;
		pkt_list = new TestPacket[pkt_lst_size];
	}

  public String name() { return "OptQueProcessPacket"; }

// ===================================================================
	//public int advance(int pos) { return (pos+1) & 0xf; }
	public int advance(int pos) { return (pos+1) % pkt_lst_size; }
//===================================================================

	public boolean insert(IInsertElem inPkt) {
		// test if advance
		if (advance(pkt_list_in) != pkt_list_out) {
			// an extra test that place is empty
			if (!pkt_list[pkt_list_in].isEmpty()) {
				System.out.println(name() + " - producer: WTF!!!");
        Utils.CRASH();
			}
			// add the packet
			pkt_list[pkt_list_in].setInfo(((InputPacket)inPkt).getInMsg());
			System.out.println(name() + " - producer:  produced packet <" + pkt_list[pkt_list_in] + ">   - in: " + pkt_list_in +  " out: " + pkt_list_out);
			// advance
			pkt_list_in = advance(pkt_list_in);
		}
		else {
			System.out.println(name() + " - producer: list full");
			return false;
		}
		// signal
		if (pkt_list_in == advance(pkt_list_out)) {
			System.out.println(name() + " - producer: signal");
		}
		return true;
	}
	
	public void consume() {
		Utils.CRASH();
	}
	
	public IProcessedElem consumeOne() {
		if (pkt_list_out != pkt_list_in) {
			// an extra test that place is NOT empty
			if (pkt_list[pkt_list_out].isEmpty()) {
				System.out.println(name() + " - consumer: WTF!!!!");
        Utils.CRASH();
			}
			
			// actually consume packet and remove it
			System.out.println(name() + " - consumer: consuming packet <" + pkt_list[pkt_list_out] + ">");
			IProcessedElem consumed = new OutputPacket(pkt_list[pkt_list_out].getInfo());
			pkt_list[pkt_list_out].clear();
			
			// advance
			pkt_list_out = advance(pkt_list_out);
			return consumed;
		}
		else {
			System.out.println(name() + " - consumer: list is empty, so wait");
			return null;
		}
	}
	
	
//===================================================================
  public void unlock() {}
}
