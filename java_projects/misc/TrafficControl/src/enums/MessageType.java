package enums;

public enum MessageType {
	LAND_REQUEST(0),
	MAYDAY(1),
	LANDED(2),
	CIRCE(3),
	LAND(4);

	public final int value;
	private MessageType(int val) {
		value = val;
	}
}
