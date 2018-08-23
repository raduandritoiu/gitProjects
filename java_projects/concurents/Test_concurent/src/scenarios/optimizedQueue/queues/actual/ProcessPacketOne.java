package scenarios.optimizedQueue.queues.actual;

import scenarios.optimizedQueue.packets.Packets;
import scenarios.optimizedQueue.packets.Packets.InputPacket;
import scenarios.optimizedQueue.packets.Packets.PacketOne_act;
import scenarios.optimizedQueue.queues.IQueue.IInsertElem;
import utils.Utils;


public class ProcessPacketOne implements IPacketProcessor {
  public int pkt_list_in = 0;
  public int pkt_list_out = 0;
  public final int pkt_lst_size;
  public final PacketOne_act[] pkt_list;

  public ProcessPacketOne(int size) { 
    pkt_lst_size = size;
    pkt_list = new PacketOne_act[pkt_lst_size];
    for (int i = 0; i < pkt_lst_size; i++)
      pkt_list[i] = new PacketOne_act();
  }
  
  public String name() { return "\t\t ProcessPacketOne"; }
  public String toString() { return "\t\t ProcessPacketOne"; }
  
//===================================================================
//  private int advance(int pos) { return (pos+1) & 0xf; }
  private int advance(int pos) { return (pos+1) % pkt_lst_size; }
//===================================================================
  
//===================================================================
  public boolean acceptPacket(IInsertElem inPkt) {
    if (((InputPacket)inPkt).type != 1) {
      return false;
    }
    
    // test if advance
    if (advance(pkt_list_in) != pkt_list_out) {
      // an extra test that place is empty
      if (pkt_list[pkt_list_in].info_one != Packets.empty_msg) {
        System.out.println(name() + " - producer: WTF!!!");
        Utils.CRASH();
      }
      // add the packet
      pkt_list[pkt_list_in].info_one = ((InputPacket)inPkt).getInMsg();
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
  
  
  public void processPacket() {
    if (pkt_list_out != pkt_list_in) {
      // an extra test that place is NOT empty
      if (pkt_list[pkt_list_out].info_one == Packets.empty_msg) {
        System.out.println(name() + " - consumer: WTF!!!!");
        Utils.CRASH();
      }
      
      // actually consume packet and remove it
      System.out.println(name() + " - consumer: consuming packet <" + pkt_list[pkt_list_out] + ">");
      pkt_list[pkt_list_out].info_one = Packets.empty_msg;
      
      // advance
      pkt_list_out = advance(pkt_list_out);
    }
    else {
      System.out.println(name() + " - consumer: list is empty, so wait");
    }
  }
}
