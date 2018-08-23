package messages;

import enums.MessageType;
import models.Runway;

public class LandMsg implements IMessage {
	private final String senderName;
	public final Runway runway;
	
	public LandMsg(String nSenderName, Runway nRunway) {
		senderName = nSenderName;
		runway = nRunway;
	}
	
	public MessageType type() {
		return MessageType.LAND;
	}
	
	public String senderName() {
		return senderName;
	}
	
	public String message() {
		return "Please land on runway " + runway.name;
	}
}
