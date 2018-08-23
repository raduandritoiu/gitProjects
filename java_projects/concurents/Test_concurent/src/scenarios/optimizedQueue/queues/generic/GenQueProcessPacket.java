package scenarios.optimizedQueue.queues.generic;

import scenarios.optimizedQueue.packets.Packets.InputPacket;
import scenarios.optimizedQueue.packets.Packets.OutputPacket;
import scenarios.optimizedQueue.packets.Packets.TestPacket;
import scenarios.optimizedQueue.queues.IQueue.IQueueElem;
import utils.Utils;


public class GenQueProcessPacket extends GenericQueue<TestPacket> implements IQueueElem {
  
	public GenQueProcessPacket(int size) { 
		super(size);
	}
	
  public String name() { return "GenQueProcessPacket"; }
  
//===================================================================
	// make this queue non-blocking
	protected void lock() {}
	protected void signal() {}

	
//===================================================================
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
	
//===================================================================
  public IQueueElem makeClone() { Utils.CRASH(); return null; }
  public String toString() { return "GenericQueueProcessPacket"; }
}
