package integration.models;

import integration.models.interfaces.IBasicModel;

public class Chair implements IBasicModel {
	private Object _val;
	
	@Override
	public String getName() {
		return "Chair - this is model from test library 1* ";
	}

	public String toString() {
		return getName();
	}
	
	@Override
	public void setValue(Object val) {
		_val = val;
	}

	@Override
	public Object getValue() {
		return _val;
	}
}
