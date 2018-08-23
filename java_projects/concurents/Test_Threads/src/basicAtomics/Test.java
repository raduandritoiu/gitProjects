package basicAtomics;

import java.util.concurrent.atomic.AtomicInteger;

import basicUtils.Resource;
import basicUtils.Utils;

public class Test
{
  public static void testInc()
  {
    Resource res = new Resource();
    
    IncThread th1 = new IncThread(res, 10000, "      <Th 1>");
    IncThread th2 = new IncThread(res, 10000, "            <Th 2>");
    IncThread th3 = new IncThread(res, 10000, "                  <Th 3>");
    IncThread th4 = new IncThread(res, 10000, "                        <Th 4>");
    IncThread th5 = new IncThread(res, 10000, "                              <Th 5>");
    IncThread th6 = new IncThread(res, 10000, "                                    <Th 6>");
    
    
    System.out.println("Start with resource: " + res.counter);

    // --- start ---
    th1.start();
    th2.start();
    th3.start();
    th4.start();
    th5.start();
    th6.start();
    
    // --- end ---
    Utils.Join(th1);
    Utils.Join(th2);
    Utils.Join(th3);
    Utils.Join(th4);
    Utils.Join(th5);
    Utils.Join(th6);
    
    // ---
    System.out.println("Ended with resource: " + res.counter + "   (expected 60000)");
  }
  
  
  public static void testIncAtomic()
  {
    AtomicInteger res = new AtomicInteger(0);
    
    AtomicIncThread th1 = new AtomicIncThread(res, 10000, "      <Th 1>");
    AtomicIncThread th2 = new AtomicIncThread(res, 10000, "            <Th 2>");
    AtomicIncThread th3 = new AtomicIncThread(res, 10000, "                  <Th 3>");
    AtomicIncThread th4 = new AtomicIncThread(res, 10000, "                        <Th 4>");
    AtomicIncThread th5 = new AtomicIncThread(res, 10000, "                              <Th 5>");
    AtomicIncThread th6 = new AtomicIncThread(res, 10000, "                                    <Th 6>");
    
    
    System.out.println("Start with resource: " + res.get());

    // --- start ---
    th1.start();
    th2.start();
    th3.start();
    th4.start();
    th5.start();
    th6.start();
    
    // --- end ---
    Utils.Join(th1);
    Utils.Join(th2);
    Utils.Join(th3);
    Utils.Join(th4);
    Utils.Join(th5);
    Utils.Join(th6);
    
    // ---
    System.out.println("Ended with resource: " + res.get() + "   (expected 60000)");
  }
}