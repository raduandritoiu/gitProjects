package scenarios.optimizedQueue.queues.optimal;

import scenarios.optimizedQueue.packets.Packets.InputPacket;
import scenarios.optimizedQueue.packets.Packets.PacketThree;


public class OptQueProcessPacketThree extends OptQueProcessPacket {
  
  public OptQueProcessPacketThree(int size) { 
    super(size);
    for (int i = 0; i < pkt_lst_size; i++)
      pkt_list[i] = new PacketThree();
  }
  
  public String name() { return "\t\t OptQueProcessPacketThree"; }
  public String toString() { return "\t\t OptimalQueProcessPacketThree"; }
  
//===================================================================
  public boolean insert(IInsertElem inPkt) {
    if (((InputPacket)inPkt).type != 3) {
      return false;
    }
    return super.insert(inPkt);
  }
}
