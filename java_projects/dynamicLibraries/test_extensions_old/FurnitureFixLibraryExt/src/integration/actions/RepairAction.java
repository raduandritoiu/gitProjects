package integration.actions;

import integration.actions.interfaces.IBasicAction;

public class RepairAction implements IBasicAction {

	@Override
	public String getName() {
		return "Repair - this is action from test library 1* ";
	}

	public String toString() {
		return getName();
	}

	@Override
	public int execute() {
		return 10;
	}

}
