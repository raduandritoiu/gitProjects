//package scenarios.optimizedQueue;
//
//import scenarios.optimizedQueue.queues.OptimalQueue;
//import scenarios.optimizedQueue.packets.Packets.IPacket;
//import scenarios.optimizedQueue.packets.Packets.TestPacket;
//
//public class ApproachThr {
//	public static int cnt;
//  
//	public static void run() throws Exception {
//		OptimalQueue q = new OptimalQueue(10);
//		Producer prod = new Producer(q);
//		Consumer cons = new Consumer(q);
//		
//		prod.start();
//		cons.start();
//		
//		try { Thread.sleep(1); }
//		catch (Exception ex) {}
//		q.run = false;
//		
//		prod.join();
//		cons.join();
//	}
//	
//	// In this approach: 
//	// - Uses METHODS on THE QUE to test the status and advance. -->> Encapsulated que
//	//
//	// - For Producer it uses 'next' to tests the next-next empty space, and this will 
//	//   always leave one empty between IN and OUT. 
//	// - This is useful at the kickstart, when IN and OUT starting at the same position 0.
//	// - Otherwise they would loop endlessly at the statup.
//	// - Also is useful so IN and OUT never get equal again.
//	
//	
//	
//	public static class Producer extends Thread {
//		public final OptimalQueue q;
//		public Producer(OptimalQueue oq) { q = oq; }
//		
//		public void run() {
//		  while (q.run) {
//			  IPacket newPkg = new TestPacket("new packet " + (cnt++));
//			  q.insert(newPkg);
//			}
//		}
//	}
//	
//	
//	public static class Consumer extends Thread {
//		public final OptimalQueue q;
//		public Consumer(OptimalQueue oq) { q = oq; }
//		
//		public void run() {
//			while (q.run) {
//				q.consume();
//			}
//		}
//	}
//}
