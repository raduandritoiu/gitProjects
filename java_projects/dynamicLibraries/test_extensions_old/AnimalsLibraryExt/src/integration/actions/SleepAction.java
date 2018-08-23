package integration.actions;

import integration.actions.interfaces.IBasicAction;

public class SleepAction implements IBasicAction {

	@Override
	public String getName() {
		return "Sleep - this is action from test library 2* ";
	}

	public String toString() {
		return getName();
	}

	@Override
	public int execute() {
		return 100;
	}
}
