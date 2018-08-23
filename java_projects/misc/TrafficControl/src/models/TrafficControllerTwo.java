package models;

import messages.LandMsg;
import messages.LandRequestMsg;
import messages.LandedMsg;
import utils.Log;


public class TrafficControllerTwo extends Thread {
	public final String name;
	private final TrafficTower tower;
	private boolean running;
	
	
	public TrafficControllerTwo(String nName, TrafficTower nTower) {
		name = nName;
		tower = nTower;
		running = true;
	}
	
	
	public void stopRunning() {
		running = false;
	}
	
	
	public void run() {
		while (running) {
			// TODO: sa ma decid daca folosesc mesajele waiting pentru a elibera pistele
			LandedMsg landMsg = null;
			// free the runways 
			while ((landMsg = tower.landedQue.poll()) != null) {
				Log.Print(landMsg, name);
				landMsg.runway.free();
			}
			
			LandRequestMsg reqMsg = null;
			// managed to acquire short runway so find a regular plane for it
			if (tower.shortRunway.tryOccupy()) {
				if ((reqMsg = tower.waitingQue.pollRegular()) != null) {
					Log.Print(reqMsg, name);
					reqMsg.airplane.sendMessage(new LandMsg(name, tower.shortRunway));
				}
				// release runway if no airplane
				else {
					tower.shortRunway.free();
				}
			}
			
			// managed to acquire short runway so find a plane for it
			if (tower.longRunway.tryOccupy()) {
				// start with waiting emergency queue and continue with waiting normal queue
				if ((reqMsg = tower.waitingQue.poll()) != null) {
					Log.Print(reqMsg, name);
					reqMsg.airplane.sendMessage(new LandMsg(name, tower.longRunway));
				}
				// release runway if no airplane
				else {
					tower.longRunway.free();
				}
			}
			
			// block until something happens
			tower.waitingQue.block(1);
		}
	}
}
