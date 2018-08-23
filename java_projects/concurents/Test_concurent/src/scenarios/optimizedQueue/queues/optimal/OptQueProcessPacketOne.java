package scenarios.optimizedQueue.queues.optimal;

import scenarios.optimizedQueue.packets.Packets.InputPacket;
import scenarios.optimizedQueue.packets.Packets.PacketOne;


public class OptQueProcessPacketOne extends OptQueProcessPacket {
  
  public OptQueProcessPacketOne(int size) { 
    super(size);
    for (int i = 0; i < pkt_lst_size; i++)
      pkt_list[i] = new PacketOne();
  }
  
  public String name() { return "\t\t OptQueProcessPacketOne"; }
  public String toString() { return "\t\t OptimalQueProcessPacketOne"; }
  
//===================================================================
  public boolean insert(IInsertElem inPkt) {
    if (((InputPacket)inPkt).type != 1) {
      return false;
    }
    return super.insert(inPkt);
  }
}
