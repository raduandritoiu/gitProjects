package scenarios.optimizedQueue.queues.actual;

import scenarios.optimizedQueue.queues.IQueue.IInsertElem;

public interface IPacketProcessor {
  String name();
  boolean acceptPacket(IInsertElem pkt);
  void processPacket();
}
