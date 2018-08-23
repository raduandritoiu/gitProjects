package scenarios.optimizedQueue.queues.generic;

import scenarios.optimizedQueue.packets.Packets.InputPacket;
import scenarios.optimizedQueue.packets.Packets.PacketThree;


public class GenQueProcessPacketThree extends GenQueProcessPacket {

	public GenQueProcessPacketThree(int size) { 
		super(size);
	}
	
  public String name() { return "\t\t GenQueProcessPacketThree"; }
  public String toString() { return "\t\t GenericQueProcessPacketThree"; }
  
//===================================================================
	protected void initQueue() {
    pkt_list = new PacketThree[pkt_lst_size];
		for (int i = 0; i < pkt_lst_size; i++)
			pkt_list[i] = new PacketThree();
	}
	
  protected boolean processInsert(IInsertElem inPkt) {
    if (((InputPacket)inPkt).type != 3) {
      return false;
    }
    return super.processInsert(inPkt);
  }
}
