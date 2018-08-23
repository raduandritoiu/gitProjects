package models;

import messages.LandMsg;
import messages.LandRequestMsg;
import utils.Log;


public class TrafficControllerOne extends Thread {
	public final String name;
	private final TrafficTower tower;
	private boolean running;
	
	
	public TrafficControllerOne(String nName, TrafficTower nTower) {
		name = nName;
		tower = nTower;
		running = true;
	}
	
	
	public void stopRunning() {
		running = false;
	}
	
	
	public void run() {
		while (running) {
			LandRequestMsg reqMsg = null;
			
			// consume the new arriving queue
			while ((reqMsg = tower.arrivingQue.poll()) != null) {
				Log.Print(reqMsg, name);
				// if there are other emergency or normal waiting then put in waiting
				if (tower.waitingQue.hasEmergency() || (!reqMsg.airplane.isEmergency && tower.waitingQue.hasNormal())) {
					tower.waitingQue.add(reqMsg);
				}
				// regular plane and managed to occupy short runway
				else if (!reqMsg.airplane.isLarge && tower.shortRunway.tryOccupy()) {
					reqMsg.airplane.sendMessage(new LandMsg(name, tower.shortRunway));
				}
				// regular/large plane and managed to occupy long runway
				else if (tower.longRunway.tryOccupy()) {
					reqMsg.airplane.sendMessage(new LandMsg(name, tower.longRunway));
				}
				// put in waiting
				else{
					tower.waitingQue.add(reqMsg);
				}
			}
			
			// block until new arrivals come
			tower.arrivingQue.block(1);
		}
	}
}
