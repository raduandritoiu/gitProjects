package radua.ui.logic.views;

import radua.ui.logic.ids.ViewId;
import radua.ui.logic.models.IBasicModel;
import radua.ui.logic.observers.IPropertyObserver;


public interface IBasicView<MDL extends IBasicModel> extends IPropertyObserver
{
	ViewId id();
	MDL model();
}
