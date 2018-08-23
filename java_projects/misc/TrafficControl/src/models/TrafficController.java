package models;

import messages.LandMsg;
import messages.LandRequestMsg;
import messages.LandedMsg;
import utils.Log;


public class TrafficController extends Thread {
	public final String name;
	private final TrafficTower tower;
	private boolean running;
	
	
	public TrafficController(String nName, TrafficTower nTower) {
		name = nName;
		tower = nTower;
		running = true;
	}
	
	
	public void stopRunning() {
		running = false;
	}
	
	
	public void run() {
		while (running) {
			int msgCnt = 0, cnt = 0;
			// free all the runways first - there can only be 2 runways 
			LandedMsg landMsg = null;
			for (; (landMsg = tower.landedQue.poll()) != null; msgCnt ++) {
				Log.Print(landMsg, name);
				landMsg.runway.free();
			}
			
			LandRequestMsg reqMsg = null;
			// managed to acquire short runway so find a regular plane for it
			if (tower.shortRunway.tryOccupy()) {
				if ((reqMsg = tower.waitingQue.pollRegular()) != null) {
					Log.Print(reqMsg, name);
					msgCnt++;
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
					msgCnt++;
					reqMsg.airplane.sendMessage(new LandMsg(name, tower.longRunway));
				}
				// release runway if no airplane
				else {
					tower.longRunway.free();
				}
			}
			
			// consume the new arriving queue - maximum 5 arrivals 
			// we do not want to get stuck in this loop and ignore ("starve") the other processing
			for (cnt = 0; (reqMsg = tower.arrivingQue.poll()) != null && cnt < 5; cnt++) {
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
			msgCnt += cnt;
			
			// block until new arrivals come
			tower.arrivingQue.block(msgCnt);
		}
	}
}
