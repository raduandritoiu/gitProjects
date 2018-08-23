package scenarios.optimizedQueue.queues.optimal;

import scenarios.optimizedQueue.packets.Packets.InputPacket;
import scenarios.optimizedQueue.packets.Packets.PacketTwo;


public class OptQueProcessPacketTwo extends OptQueProcessPacket {
  
  public OptQueProcessPacketTwo(int size) { 
    super(size);
    for (int i = 0; i < pkt_lst_size; i++)
      pkt_list[i] = new PacketTwo();
  }
  
  public String name() { return "\t\t OptQueProcessPacketTwo"; }
  public String toString() { return "\t\t OptimalQueProcessPacketTwo"; }
  
//===================================================================
  public boolean insert(IInsertElem inPkt) {
    if (((InputPacket)inPkt).type != 1) {
      return false;
    }
    return super.insert(inPkt);
  }
}
