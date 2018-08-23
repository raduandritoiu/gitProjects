package blockingQueue;

import java.util.LinkedList;

public class StdNonSyncQue<E> extends LinkedList<E> implements IBlockingQue<E>
{
  static final long serialVersionUID = 324;
  
  public StdNonSyncQue(int c)
  {
    super();
  }
  
  public void put(E e) throws InterruptedException
  {
    super.push(e);
  }
  
  
  public E take() throws InterruptedException
  {
    return super.poll();
  }
}
