package blockingQueue;

public interface IBlockingQue<E>
{
  int size();
  void put(E e) throws InterruptedException;
  E take() throws InterruptedException;
}
