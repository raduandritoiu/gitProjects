package messages;

import enums.MessageType;
import models.Airplane;

public class MaydayMsg extends LandRequestMsg {
	public MaydayMsg(Airplane nPlane) {
		super(nPlane);
	}
	
	public MessageType type() {
		return MessageType.MAYDAY;
	}
	
	public String message() {
		return "Mayday";
	}
}
