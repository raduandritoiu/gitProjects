package blockingQueue;

import java.util.concurrent.LinkedBlockingQueue;


public class StdSyncQue<E> extends LinkedBlockingQueue<E> implements IBlockingQue<E>
{
  static final long serialVersionUID = 324;
  
  public StdSyncQue(int c)
  {
    super(c);
  }
}
