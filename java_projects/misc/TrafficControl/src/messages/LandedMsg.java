package messages;

import enums.MessageType;
import models.Runway;

public class LandedMsg implements IMessage {
	private final String senderName;
	public final Runway runway;

	public LandedMsg(String nSenderName, Runway nRunway) {
		senderName = nSenderName;
		runway = nRunway;
	}
	
	public MessageType type() {
		return MessageType.LANDED;
	}
	
	public String senderName() {
		return senderName;
	}
	
	public String message() {
		return "Landed";
	}
}
