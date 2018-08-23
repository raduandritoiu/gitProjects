package scenarios.optimizedQueue.queues.generic;

import utils.Utils;

public class GenQuePackets extends GenericQueue<GenQueProcessPacket> {
	private final GenQueProcessPacket[] processors;
	
	
	public GenQuePackets(int size) { 
		super(size);
		processors = new GenQueProcessPacket[3];
		processors[0] = new GenQueProcessPacketOne(3);
    processors[1] = new GenQueProcessPacketTwo(5);
    processors[2] = new GenQueProcessPacketThree(4);
	}
	
  public String name() { return "GenQuePacket"; }
  
//===================================================================
  protected void initQueue() {
    pkt_list = new GenQueProcessPacket[pkt_lst_size];
  }

	protected void testInsert() {
		if (pkt_list[pkt_list_in] != null) {
			System.out.println(name() + " - producer: WTF!!!");
      Utils.CRASH();
		}
	}
	
	protected void testConsume() {
		if (pkt_list[pkt_list_out] == null) {
			System.out.println(name() + " - consumer: WTF!!!!");
      Utils.CRASH();
		}
	}
	
	protected boolean processInsert(IInsertElem inPkt) {
		boolean proc = false;
		int i = 0;
		for (i = 0 ; i < processors.length && !proc; i++) {
			proc = processors[i].insert(inPkt);
		}
		if (proc) {
			pkt_list[pkt_list_in] = processors[i-1];
		}
		System.out.println(name() + " - producer:  produced packet <" + pkt_list[pkt_list_in] + ">   - in: " + pkt_list_in +  " out: " + pkt_list_out);
		return proc;
	}
	
	protected IProcessedElem processConsume() {
	  IProcessedElem consumed = ((GenQueProcessPacket)pkt_list[pkt_list_out]).consumeOne();
		pkt_list[pkt_list_out] = null;
		System.out.println(name() + " - consumer: consuming packet <" + pkt_list[pkt_list_out] + ">");
		return consumed;
	}
}
