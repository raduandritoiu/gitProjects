package messages;

import enums.MessageType;

public interface IMessage {
	MessageType type();
	String senderName();
	String message();
}
