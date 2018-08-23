package scenarios.optimizedQueue.queues.generic;

import scenarios.optimizedQueue.packets.Packets.InputPacket;
import scenarios.optimizedQueue.packets.Packets.PacketTwo;


public class GenQueProcessPacketTwo extends GenQueProcessPacket {

	public GenQueProcessPacketTwo(int size) { 
		super(size);
	}
	
  public String name() { return "\t\t GenQueProcessPacketTwo"; }
  public String toString() { return "\t\t GenericQueProcessPacketTwo"; }
  
//===================================================================
	protected void initQueue() {
    pkt_list = new PacketTwo[pkt_lst_size];
		for (int i = 0; i < pkt_lst_size; i++)
			pkt_list[i] = new PacketTwo();
	}
	
  protected boolean processInsert(IInsertElem inPkt) {
    if (((InputPacket)inPkt).type != 2) {
      return false;
    }
    return super.processInsert(inPkt);
  }
}
