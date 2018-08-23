package syncs;

import java.util.concurrent.ConcurrentLinkedQueue;

import messages.LandedMsg;


public class LandedQueue implements IBlockingQueue<LandedMsg> {
	private final IBlockingMutex queLock;
	public final ConcurrentLinkedQueue<LandedMsg> que;
	
	public LandedQueue() {
		this(new SimpleMutex(false));
	}
	
	public LandedQueue(IBlockingMutex lock) {
		queLock = lock;
		que = new ConcurrentLinkedQueue<>();
	}
	
	
	public void add(LandedMsg msg) {
		que.add(msg);
		queLock.unlock(1);
	}
	
	
	public LandedMsg poll() {
		return que.poll();
	}

	
	public void block(int permits) {
		queLock.lock(permits);
	}
}
