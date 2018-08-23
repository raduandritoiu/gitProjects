import models.Airplane;
import models.ITrafficTower;
import models.TrafficController;
import models.TrafficControllerOne;
import models.TrafficControllerTwo;
import models.TrafficTower;
import syncs.ArrivingQueue;
import syncs.LandedQueue;
import syncs.SemaphorMutex;
import syncs.SimpleMutex;
import syncs.WaitingQueue;


public class Main {
	public static void main(String[] args) throws Exception {
		run_Traffic_specialized();
	}
	
	
	private static void run_Traffic_common() throws Exception {
		// initialize models
		SemaphorMutex mutex = new SemaphorMutex(0);
		ArrivingQueue arrivingQue = new ArrivingQueue(mutex);
		WaitingQueue waitingQue = new WaitingQueue(mutex);
		LandedQueue landedQue = new LandedQueue(mutex);
		TrafficTower tower = new TrafficTower(arrivingQue, waitingQue, landedQue);
		
		// initialize controllers
		TrafficController controllerOne = new TrafficController("Tower 1", tower);
		TrafficController controllerTwo = new TrafficController("Tower 2", tower);
		// initialize planes
		Airplane[] airplanes = initializePlanes(tower, "");
		
		// start controllers
		controllerOne.start();
		controllerTwo.start();
		// start planes
		for (int i = 0; i < airplanes.length; i++) {
			airplanes[i].start();
		}
		
		// wait for planes
		for (int i = 0; i < airplanes.length; i++) {
			airplanes[i].join();
		}
		// wait for controllers
		controllerOne.join();
		controllerTwo.join();
		
		//end
		System.out.println("DONE!!!");
	}
	
	
	private static void run_Traffic_specialized() throws Exception {
		// initialize models
		ArrivingQueue arrivingQue = new ArrivingQueue();
		// waiting and landing must share the mutex because controller two blocks for both
		SimpleMutex mutex = new SimpleMutex(false);
		WaitingQueue waitingQue = new WaitingQueue(mutex);
		LandedQueue landedQue = new LandedQueue(mutex);
		TrafficTower tower = new TrafficTower(arrivingQue, waitingQue, landedQue);
		
		// initialize controllers
		TrafficControllerOne controllerOne = new TrafficControllerOne("Tower 1", tower);
		TrafficControllerTwo controllerTwo = new TrafficControllerTwo("Tower 2", tower);
		// initialize planes
		Airplane[] airplanes = initializePlanes(tower, "");
		
		// start controllers
		controllerOne.start();
		controllerTwo.start();
		// start planes
		for (int i = 0; i < airplanes.length; i++) {
			airplanes[i].start();
		}
		
		// wait for planes
		for (int i = 0; i < airplanes.length; i++) {
			airplanes[i].join();
		}
		// wait for controllers
		controllerOne.join();
		controllerTwo.join();
		
		//end
		System.out.println("DONE!!!");
	}

	
	private static Airplane[] initializePlanes(ITrafficTower tower, String configName) {
		return new Airplane[3];
	}
}
