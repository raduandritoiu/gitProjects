package syncs;

public interface IBlockingMutex {
    void lock(int permits);
    void unlock(int permits);
    boolean isOpen();
}
