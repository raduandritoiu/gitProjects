package blockingQueue;

import java.util.LinkedList;

import basicUtils.MonitorFromObj;


public class QueFromObj<E> implements IBlockingQue<E>
{
  private final int capacity;
  private final LinkedList<E> que;
  private final MonitorFromObj headLock;
  private final MonitorFromObj tailLock;
  
  
  public QueFromObj(int c)
  {
    capacity = c;
    que = new LinkedList<>();
    headLock = new MonitorFromObj();
    tailLock = new MonitorFromObj();
  }
  
  public int size()
  {
    return que.size();
  }
  
  public void put(E e) throws InterruptedException
  {
    synchronized (headLock)
    {
      for (;;)
      {
        if (que.size() < capacity)
        {
          que.push(e);
          synchronized (tailLock)
          {
            tailLock.notifyAll();
          }
          break; // return;
        }
        else
        {
          headLock.wait();
        }
      }
    }
  }
  
  public E take() throws InterruptedException
  {
    E res;
    synchronized (tailLock)
    {
      for (;;)
      {
        if (que.size() > 0)
        {
          res = que.poll();
          synchronized (headLock)
          {
            headLock.notifyAll();
          }
          break;
        }
        else
        {
          tailLock.wait();
        }
      }
    }
    return res;
  }
}
