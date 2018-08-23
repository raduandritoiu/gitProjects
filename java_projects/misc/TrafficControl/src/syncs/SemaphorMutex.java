package syncs;

import java.util.concurrent.Semaphore;

public class SemaphorMutex implements IBlockingMutex {
	private final Semaphore _semapthore;

	public SemaphorMutex(int permits) { _semapthore = new Semaphore(permits); }

	public void lock(int permits) { 
		try {
			_semapthore.acquire(permits); 
		}
		catch (Exception ex) {}
	}
	public void unlock(int permits) { _semapthore.release(permits); }
	public boolean isOpen() { return ( _semapthore.availablePermits() > 0); }
}
