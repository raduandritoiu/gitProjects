package integration.actions;

import integration.actions.interfaces.IBasicAction;

public class FixAction implements IBasicAction {

	@Override
	public String getName() {
		return "Fix - this is action from test library 1* ";
	}

	public String toString() {
		return getName();
	}

	@Override
	public int execute() {
		return 1;
	}
}
