package models;

import java.util.concurrent.ConcurrentLinkedQueue;

import enums.MessageType;
import messages.IMessage;
import messages.LandMsg;
import messages.LandRequestMsg;
import messages.LandedMsg;
import messages.MaydayMsg;
import utils.Log;


public class Airplane extends Thread{
	public final String name;
	public final boolean isLarge;
	public final boolean isEmergency;
	private final int delay;
	private final ITrafficTower trafficTower;
	private final ConcurrentLinkedQueue<IMessage> msgQue;
	
	
	public Airplane(String nName, boolean nIsLarge, boolean nIsEmergency, int nDelay, ITrafficTower nTower) {
		name = nName;
		isLarge = nIsLarge;
		isEmergency = nIsEmergency;
		delay = nDelay;
		trafficTower = nTower;
		msgQue = new ConcurrentLinkedQueue<>();
	}
	
	
	private void flyMore() {
		try {
			Thread.sleep(delay * 1000);
		}
		catch (Exception ex) {
			Log.Print(name + " woke up and had to land earlier!");
		}
	}
	
	
	private void circleAround() {
		// do nothing for now,
		// just wait for next message
	}
	
	
	private void land() {
		int landTime = isLarge ? 7000 : 5000;
		try {
			Thread.sleep(landTime);
		}
		catch (Exception ex) {
			Log.Print(name + " woke up and landing took less than " + delay + "!");
		}
	}
	
	
	private void landed(Runway runway) {
		// TODO: sa ma mai gandesc daca eliberez eu pista sau trimit mesaj la turn
		trafficTower.sendMessage(new LandedMsg(name, runway));
	}
	
	
	public void sendMessage(IMessage msg) {
		msgQue.add(msg);
	}
	
	
	public void run() {
		// fly before requesting to land
		flyMore();
		
		// send request to land
		LandRequestMsg landReq = isEmergency ? new MaydayMsg(this) : new LandRequestMsg(this);
		Log.Print(landReq);
		trafficTower.sendMessage(landReq);
		
		// wait for instructions from tower
		boolean running = true;
		while (running) {
			IMessage msg = msgQue.poll();
			Log.Print(msg, name);
			if (msg.type() == MessageType.CIRCE) {
				circleAround();
			}
			else if (msg.type() == MessageType.LAND) {
				land();
				Runway runway = ((LandMsg) msg).runway;
				landed(runway);
				running = false;
			}
		}
	}
}
