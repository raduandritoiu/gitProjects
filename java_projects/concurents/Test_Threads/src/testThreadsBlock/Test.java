package testThreadsBlock;

import basicUtils.Utils;
import sun.misc.Unsafe;
import testUnsafe.MyOwnUnsafe;

public class Test
{
  public static void test()
  {
    testExteriorWithThread();
  }
  
  public static void testExteriorWithThread()
  {
    Unsafe unsafe = MyOwnUnsafe.getInstance();
    RunningThread th = new RunningThread(6000, 6000, "<Th 1>");
    th.start();
    
    Utils.Sleep(100);
    
    // block the thread
    th.suspend();
    // sleep
    Utils.print = false;
    Utils.Sleep(1000);
    Utils.print = true;
    // resume
    th.resume();
    
    // block the thread
    th.suspend();
    // sleep
    Utils.print = false;
    Utils.Sleep(1000);
    Utils.print = true;
    // resume
    th.resume();
    
    Utils.Join(th);
  }
  
  
  public static void testWithUnsafe()
  {
    BlockThreadUnsafe bl1 = new BlockThreadUnsafe(3, "<Th 1>");
    NotifyThreadUnsafe st1 = new NotifyThreadUnsafe(bl1, 3, "                    <Th 2>");
    
    bl1.start();
    st1.start();
    
    Utils.Join(bl1);
    Utils.Join(st1);
  }
  
  public static void testWithTh()
  {
    BlockThread bl1 = new BlockThread(3, "<Th 1>");
    NotifyThread st1 = new NotifyThread(bl1, 3, "                    <Th 2>");
    
    bl1.start();
    st1.start();
    
    Utils.Join(bl1);
    Utils.Join(st1);
  }
  
  public static void testWithObj()
  {
    Object lock = new Object();
    
    BlockThreadObj bl1 = new BlockThreadObj(lock, 3, "<Th 1>");
//    BlockThreadObj bl2 = new BlockThreadObj(lock, 3, "    <Th 2>");
    NotifyThreadObj st1 = new NotifyThreadObj(lock, 3, "                    <Th 2>");
    
    bl1.start();
//    bl2.start();
    st1.start();
    
    Utils.Join(bl1);
//    Utils.Join(bl2);
    Utils.Join(st1);
  }
}
