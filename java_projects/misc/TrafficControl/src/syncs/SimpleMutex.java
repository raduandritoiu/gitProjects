package syncs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class SimpleMutex extends AbstractQueuedSynchronizer implements IBlockingMutex {
	private static final long serialVersionUID = 4982264981922014374L;

	public SimpleMutex(boolean open) { setState(open ? 1 : 0); }

	protected boolean tryAcquire(int acquires) { return compareAndSetState(1, 0); }
	protected boolean tryRelease(int releases) { return compareAndSetState(0, 1); }
      
	public void lock(int permits) { acquire(1); }
	public void unlock(int permits) { release(1); }
	public boolean isOpen() { return (getState() == 1); }
}
