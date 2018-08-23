package integration.actions;

import integration.actions.interfaces.IBasicAction;

public class EatAction implements IBasicAction {

	@Override
	public String getName() {
		return "Eat - this is action from test library 2* ";
	}

	public String toString() {
		return getName();
	}

	@Override
	public int execute() {
		return 1;
	}
}
