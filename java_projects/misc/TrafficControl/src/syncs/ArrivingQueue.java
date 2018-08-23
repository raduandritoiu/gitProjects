package syncs;

import java.util.concurrent.ConcurrentLinkedQueue;

import enums.MessageType;
import messages.LandRequestMsg;
import messages.MaydayMsg;


public class ArrivingQueue implements IBlockingQueue<LandRequestMsg> {
	private final IBlockingMutex queLock;
	private final ConcurrentLinkedQueue<LandRequestMsg> normalQue;
	private final ConcurrentLinkedQueue<LandRequestMsg> emergencyQue;

	
	public ArrivingQueue() {
		this(new SimpleMutex(false));
	}
	
	public ArrivingQueue(IBlockingMutex lock) {
		queLock = lock;
		normalQue = new ConcurrentLinkedQueue<>();
		emergencyQue = new ConcurrentLinkedQueue<>();
	}
	
	
	public void add(LandRequestMsg msg) {
		if (msg.type() == MessageType.LAND_REQUEST)
			normalQue.add((LandRequestMsg)msg);
		else if (msg.type() == MessageType.MAYDAY)
			emergencyQue.add((MaydayMsg)msg);
		queLock.unlock(1);
	}
	
	
	public LandRequestMsg poll() {
		LandRequestMsg msg = emergencyQue.poll();
		if (msg == null)
			msg = normalQue.poll();
		return msg;
	}
	
	
	public void block(int permits) {
		queLock.lock(permits);
	}
}
