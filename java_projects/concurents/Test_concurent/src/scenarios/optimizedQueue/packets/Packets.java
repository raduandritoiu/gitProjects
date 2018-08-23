package scenarios.optimizedQueue.packets;

import scenarios.optimizedQueue.queues.IQueue.IInsertElem;
import scenarios.optimizedQueue.queues.IQueue.IProcessedElem;
import scenarios.optimizedQueue.queues.IQueue.IQueueElem;

public class Packets {
  public static final String empty_msg = "empty";
  
  
  public static class InputPacket implements IInsertElem {
    protected String msg;
    public final int type;
    public InputPacket(String nMsg) { msg = nMsg; type = 0; }
    public InputPacket(String nMsg, int t) { msg = nMsg; type = t; }
    public String getInMsg() { return msg; }
    public String toString() { return msg; }
  }


  public static class OutputPacket implements IProcessedElem {
    protected String msg;
    public OutputPacket(String nMsg) { msg = nMsg; }
    public String getOutMsg() { return msg; }
    public String toString() { return msg; }
  }
  

	public static class TestPacket implements IQueueElem {
		protected String msg = empty_msg;
    public TestPacket() { System.out.println("=============== THIS SHOULD NOT BE HERE ==============="); }
    public boolean isEmpty() { return msg == empty_msg; }
    public void clear() { msg = empty_msg; }
		public void setInfo(String str) { msg = str; }
		public String getInfo() { return msg; }
    public String toString() { return msg; }
	}
	
  public static class PacketOne extends TestPacket {
    public PacketOne() { System.out.println("=============== THIS SHOULD NOT BE HERE ==============="); }
  }
  
  public static class PacketTwo extends TestPacket {
    public PacketTwo() { System.out.println("=============== THIS SHOULD NOT BE HERE ==============="); }
  }
  
  public static class PacketThree extends TestPacket {
    public PacketThree() { System.out.println("=============== THIS SHOULD NOT BE HERE ==============="); }
  }
  

  
  public static class PacketOne_act {
    public String info_one = empty_msg;
    public PacketOne_act() { System.out.println("=============== THIS SHOULD NOT BE HERE ==============="); }
    public String toString() { return info_one; }
  }
  
  public static class PacketTwo_act {
    public String info_two = empty_msg;
    public PacketTwo_act() { System.out.println("=============== THIS SHOULD NOT BE HERE ==============="); }
    public String toString() { return info_two; }
  }
  
  public static class PacketThree_act {
    public String info_three = empty_msg;
    public PacketThree_act() { System.out.println("=============== THIS SHOULD NOT BE HERE ==============="); }
    public String toString() { return info_three; }
  }
}
