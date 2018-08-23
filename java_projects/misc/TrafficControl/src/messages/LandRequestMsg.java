package messages;

import java.util.concurrent.atomic.AtomicBoolean;

import enums.MessageType;
import models.Airplane;

public class LandRequestMsg implements IMessage {
	public final Airplane airplane;
	private final AtomicBoolean handled;
	
	public LandRequestMsg(Airplane nPlane) {
		airplane = nPlane;
		handled = new AtomicBoolean(false);
	}
	
	public MessageType type() {
		return MessageType.LAND_REQUEST;
	}
	
	public String senderName() {
		return airplane.name;
	}
	
	public boolean tryHandle() {
		if (airplane.isLarge)
			return true;
		return handled.compareAndSet(false, true);
	}
	
	public String message() {
		return "Ready To Land";
	}
}
