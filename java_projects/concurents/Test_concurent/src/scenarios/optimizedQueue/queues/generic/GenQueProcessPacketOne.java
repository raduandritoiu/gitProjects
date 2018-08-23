package scenarios.optimizedQueue.queues.generic;

import scenarios.optimizedQueue.packets.Packets.InputPacket;
import scenarios.optimizedQueue.packets.Packets.PacketOne;


public class GenQueProcessPacketOne extends GenQueProcessPacket {

	public GenQueProcessPacketOne(int size) { 
		super(size);
	}
	
  public String name() { return "\t\t GenQueProcessPacketOne"; }
  public String toString() { return "\t\t GenericQueProcessPacketOne"; }
  
//===================================================================
	protected void initQueue() {
	  pkt_list = new PacketOne[pkt_lst_size];
	  for (int i = 0; i < pkt_lst_size; i++)
			pkt_list[i] = new PacketOne();
	}
	
  protected boolean processInsert(IInsertElem inPkt) {
    if (((InputPacket)inPkt).type != 1) {
      return false;
    }
    return super.processInsert(inPkt);
  }
}
