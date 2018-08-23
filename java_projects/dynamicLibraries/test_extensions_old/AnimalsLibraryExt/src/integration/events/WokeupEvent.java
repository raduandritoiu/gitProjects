package integration.events;

import integration.events.interfaces.IBasicEvent;

public class WokeupEvent implements IBasicEvent {

	@Override
	public String getName() {
		return "Woke Up - this event is from test library 2* ";
	}

	public String toString() {
		return getName();
	}

	@Override
	public int dispatchEvent() {
		return 1;
	}

}
