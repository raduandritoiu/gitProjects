package models;

import messages.IMessage;
import messages.LandRequestMsg;
import messages.LandedMsg;
import syncs.ArrivingQueue;
import syncs.LandedQueue;
import syncs.WaitingQueue;
import utils.Log;


public class TrafficTower implements ITrafficTower {
	public final Runway shortRunway;
	public final Runway longRunway;
	public final ArrivingQueue arrivingQue;
	public final WaitingQueue waitingQue;
	public final LandedQueue landedQue;
	
	
	public TrafficTower(ArrivingQueue nArrivingQue, WaitingQueue nWaitingQuenew, LandedQueue nLandedQue) {
		shortRunway = new Runway("Runway 1", false);
		longRunway = new Runway("Runway 2", true);
		arrivingQue = nArrivingQue;
		waitingQue = nWaitingQuenew;
		landedQue = nLandedQue;
	}
	
	
	public void sendMessage(IMessage msg) {
		switch (msg.type()) {
			case LAND_REQUEST:
			case MAYDAY:
				arrivingQue.add((LandRequestMsg)msg);
				break;
				
			case LANDED:
				landedQue.add((LandedMsg)msg);
				break;
			
			default:
				Log.Print("Should have never received this message:" + msg);
		}
	}
}
