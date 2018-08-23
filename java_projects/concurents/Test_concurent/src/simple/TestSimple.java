package simple;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class TestSimple
{
  public static void test()
  {
    Cat cat = new Cat();
    
    
    System.out.println("Cat is owned by us " + Thread.holdsLock(cat));
    cat.aging();
    
    synchronized (cat)
    {
      cat.aging();
      System.out.println("Cat is owned by us " + Thread.holdsLock(cat));
      cat.aging();
    }
    
    cat.aging();
    System.out.println("Cat is owned by us " + Thread.holdsLock(cat));
    
    synchronized (cat)
    {
      bla(cat, 8);
    }
    
    bla(cat, 7);
  }
  
  
  public static void bla(Cat cat, int a)
  {
    cat.aging(a);
    System.out.println("Cat is owned by us " + Thread.holdsLock(cat));
    AbstractQueuedSynchronizer quw;
  }
}
