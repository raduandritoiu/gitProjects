package producer_consumer;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestProcCons
{
  public static void test()
  {
    Lock lock = new ReentrantLock();
    lock.newCondition();
  }
}
