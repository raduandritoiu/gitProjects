package radua.ui.logic.view;

import radua.ui.logic.ids.ViewId;
import radua.ui.logic.models.IBasicModel;
import radua.ui.logic.observers.IPropertyObserver;


public interface IModelView<MDL extends IBasicModel> extends IPropertyObserver
{
	ViewId id();
	MDL model();
}
