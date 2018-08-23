package integration.actions;

import integration.actions.interfaces.IBasicAction;

public class DieAction implements IBasicAction {

	@Override
	public String getName() {
		return "Die - this is action from test library 2* ";
	}

	public String toString() {
		return getName();
	}

	@Override
	public int execute() {
		return 10;
	}

}
