package integration.events;

import integration.events.interfaces.IBasicEvent;

public class BrokeEvent implements IBasicEvent {

	@Override
	public String getName() {
		return "Broke - this event is from test library 1* ";
	}

	public String toString() {
		return getName();
	}

	@Override
	public int dispatchEvent() {
		return 1;
	}

}
