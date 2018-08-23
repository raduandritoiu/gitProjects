package basicObject;

import basicUtils.MonitorFromObj;
import basicUtils.Utils;

public class Test
{
  public static void testSync()
  {
    // mai multe threaduri care se blocheaza la
    MonitorFromObj monitor = new MonitorFromObj();
    
    SyncThread th1 = new SyncThread(monitor, "      <Th 1>");
    SyncThread th2 = new SyncThread(monitor, "                        <Th 2>");
    SyncThread th3 = new SyncThread(monitor, "                                          <Th 3>");
    SyncThread th4 = new SyncThread(monitor, "                                                            <Th 4>");
    
    // --- start ---
    th1.start();
    th2.start();
    th3.start();
    th4.start();
    
    // --- end ---
    Utils.Join(th1);
    Utils.Join(th2);
    Utils.Join(th3);
    Utils.Join(th4);
  }
  
  
  public static void testBlock()
  {
    // mai multe threaduri care se blocheaza la
    MonitorFromObj monitor = new MonitorFromObj();
    
    BlockingThread th1 = new BlockingThread(monitor, "      <Th 1>");
    BlockingThread th2 = new BlockingThread(monitor, "                        <Th 2>" );
    BlockingThread th3 = new BlockingThread(monitor, "                                          <Th 3>");
    BlockingThread th4 = new BlockingThread(monitor, "                                                            <Th 4>");
    
    // --- start ---
    th1.start();
    th2.start();
    th3.start();
    th4.start();
    
    // ---
    Utils.Sleep(10000);
    System.out.println("Signal one");
    synchronized (monitor)
    {
//    monitor.notifyAll(); // - notifica toate threadurile care asteapta
      monitor.notify(); // - notifica doar un thread din care asteapta
      monitor.notify(); // - notifica doar un thread din care asteapta
      monitor.notify(); // - notifica doar un thread din care asteapta
      monitor.notify(); // - notifica doar un thread din care asteapta
    }
    
    // --- end ---
    Utils.Join(th1);
    Utils.Join(th2);
    Utils.Join(th3);
    Utils.Join(th4);
  }
}
