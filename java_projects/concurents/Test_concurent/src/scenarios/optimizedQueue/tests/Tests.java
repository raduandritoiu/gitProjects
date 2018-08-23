package scenarios.optimizedQueue.tests;

import scenarios.optimizedQueue.queues.IQueue;
import scenarios.optimizedQueue.queues.actual.PacketsQueue;
import scenarios.optimizedQueue.queues.generic.GenQuePackets;
import scenarios.optimizedQueue.queues.generic.GenQueTest;
import scenarios.optimizedQueue.queues.optimal.OptQuePackets;
import scenarios.optimizedQueue.queues.optimal.OptimalQueue;

public class Tests {
  
  
  public static void run_Generic_Single() throws Exception {
    IQueue q = new GenQueTest(10);
    Thread prod = new TestUtils.SingleProducer(q);
    TestUtils.startTest(q, prod);
  }

  
  public static void run_Generic_Multiple() throws Exception {
    IQueue q = new GenQuePackets(10);
    Thread prod = new TestUtils.MultipleProducer(q, false);
    TestUtils.startTest(q, prod);
  }
  
  
  public static void run_Generic_Multiple_random() throws Exception {
    IQueue q = new GenQuePackets(10);
    Thread prod = new TestUtils.MultipleProducer(q, true);
    TestUtils.startTest(q, prod);
  }
  
  
  public static void run_Optimal_Single() throws Exception {
    IQueue q = new OptimalQueue(10);
    Thread prod = new TestUtils.SingleProducer(q);
    TestUtils.startTest(q, prod);
  }
  
  
  public static void run_Optimal_Multiple() throws Exception {
    IQueue q = new OptQuePackets(10);
    Thread prod = new TestUtils.MultipleProducer(q, false);
    TestUtils.startTest(q, prod);
  }
  
  
  public static void run_Optimal_Multiple_random() throws Exception {
    IQueue q = new OptQuePackets(10);
    Thread prod = new TestUtils.MultipleProducer(q, true);
    TestUtils.startTest(q, prod);
  }
  
  
  public static void run_Actual() throws Exception {
    IQueue q = new PacketsQueue(10);
    Thread prod = new TestUtils.MultipleProducer(q, false);
    TestUtils.startTest(q, prod);
  }
  
  
  public static void run_Actual_random() throws Exception {
    IQueue q = new PacketsQueue(10);
    Thread prod = new TestUtils.MultipleProducer(q, true);
    TestUtils.startTest(q, prod);
  }

}
