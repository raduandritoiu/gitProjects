package integration.events;

import integration.events.interfaces.IBasicEvent;

public class FixedEvent implements IBasicEvent {

	@Override
	public String getName() {
		return "Fixed - this event is from test library 1* ";
	}

	public String toString() {
		return getName();
	}

	@Override
	public int dispatchEvent() {
		return 2;
	}

}
