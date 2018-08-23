package integration.models.interfaces;

import integration.general.interfaces.IGenericObject;

public interface IBasicModel extends IGenericObject
{
	void setValue(Object val);
	Object getValue();
}
