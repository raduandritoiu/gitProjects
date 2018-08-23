package integration.events;

import integration.events.interfaces.IBasicEvent;

public class MadeSoundEvent implements IBasicEvent {

	@Override
	public String getName() {
		return "Made Sound - this event is from test library 2* ";
	}

	public String toString() {
		return getName();
	}

	@Override
	public int dispatchEvent() {
		return 2;
	}

}
