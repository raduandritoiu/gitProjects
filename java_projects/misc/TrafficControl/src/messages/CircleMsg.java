package messages;

import enums.MessageType;

public class CircleMsg implements IMessage {
	private final String senderName;
	
	public CircleMsg(String nSenderName) {
		senderName = nSenderName;
	}
	
	public MessageType type() {
		return MessageType.CIRCE;
	}
	
	public String senderName() {
		return senderName;
	}
	
	public String message() {
		return "Please circle around the airport";
	}
}
