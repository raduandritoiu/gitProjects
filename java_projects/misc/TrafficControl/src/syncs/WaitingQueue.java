package syncs;

import java.util.concurrent.ConcurrentLinkedQueue;

import messages.LandRequestMsg;


public class WaitingQueue implements IBlockingQueue<LandRequestMsg> {
	private final IBlockingMutex queLock;
	private final ConcurrentLinkedQueue<LandRequestMsg> normalAllQue;
	private final ConcurrentLinkedQueue<LandRequestMsg> emergencyAllQue;
	private final ConcurrentLinkedQueue<LandRequestMsg> normalRegularQue;
	private final ConcurrentLinkedQueue<LandRequestMsg> emergencyRegularQue;
	
	
	public WaitingQueue() {
		this(new SimpleMutex(false));
	}

	public WaitingQueue(IBlockingMutex lock) {
		queLock = lock;
		normalAllQue = new ConcurrentLinkedQueue<>();
		emergencyAllQue = new ConcurrentLinkedQueue<>();
		normalRegularQue = new ConcurrentLinkedQueue<>();
		emergencyRegularQue = new ConcurrentLinkedQueue<>();
	}
	

	public void add(LandRequestMsg msg) {
		if (msg.airplane.isEmergency) {
			emergencyAllQue.add(msg);
			if (!msg.airplane.isLarge) 
				emergencyRegularQue.add(msg);
		}
		else {
			normalAllQue.add(msg);
			if (!msg.airplane.isLarge) 
				normalRegularQue.add(msg);
		}
		queLock.unlock(1);
	}
	
	
	public LandRequestMsg pollRegular() {
		LandRequestMsg msg = null;
		// start with waiting emergency regular queue
		while ((msg = emergencyRegularQue.poll()) != null && !msg.tryHandle()) {}
		// continue with waiting normal regular queue
		if (msg == null) {
			while ((msg = normalRegularQue.poll()) != null && !msg.tryHandle()) {}
		}
		return msg;
	}
	
	public LandRequestMsg poll() {
		LandRequestMsg reqMsg = null;
		// start with waiting emergency queue
		while ((reqMsg = emergencyAllQue.poll()) != null && !reqMsg.tryHandle()) {}
		// continue with waiting normal queue
		if (reqMsg == null) {
			while ((reqMsg = normalAllQue.poll()) != null && !reqMsg.tryHandle()) {}
		}
		return reqMsg;
	}
	
	
	public boolean hasEmergency() {
		return !emergencyAllQue.isEmpty();
	}
	
	public boolean hasNormal() {
		return !normalAllQue.isEmpty();
	}
	
	
	public void block(int permits) {
		queLock.lock(permits);
	}
}
