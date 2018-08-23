package scenarios.optimizedQueue.queues.generic;

import scenarios.optimizedQueue.packets.Packets.InputPacket;
import scenarios.optimizedQueue.packets.Packets.OutputPacket;
import scenarios.optimizedQueue.packets.Packets.TestPacket;
import utils.Utils;


public class GenQueTest extends GenericQueue<TestPacket> {
  
	public GenQueTest(int size) {
	  super(size);
	}

	public String name() { return "GenQueTest"; }

//===================================================================
	protected void initQueue() {
    pkt_list = new TestPacket[pkt_lst_size];
    for (int i = 0; i < pkt_lst_size; i++)
      pkt_list[i] = new TestPacket();
  }

	protected void testInsert() {
		if (!pkt_list[pkt_list_in].isEmpty()) {
			System.out.println(name() + " - producer: WTF!!! - producing slot NOT empty");
      Utils.CRASH();
		}
	}
	
	protected void testConsume() {
		if (pkt_list[pkt_list_out].isEmpty()) {
			System.out.println(name() + " - consumer: WTF!!!! - consuming slot IS empty");
			Utils.CRASH();
		}
	}
	
	protected boolean processInsert(IInsertElem inPkt) {
	  int cnt = Integer.parseInt(((InputPacket)inPkt).getInMsg().substring(11));
	  if (cnt % 5 ==0)
	    return false;
		pkt_list[pkt_list_in].setInfo(((InputPacket)inPkt).getInMsg());
		System.out.println(name() + " - producer: inserting packet <" + pkt_list[pkt_list_in] + ">   - in: " + pkt_list_in +  " out: " + pkt_list_out);
		return true;
	}
	
	protected IProcessedElem processConsume() {
		System.out.println(name() + " - consumer: consuming packet <" + pkt_list[pkt_list_out] + ">");
		IProcessedElem consumed = new OutputPacket(pkt_list[pkt_list_out].getInfo());
		pkt_list[pkt_list_out].clear();
		return consumed;
	}
}
