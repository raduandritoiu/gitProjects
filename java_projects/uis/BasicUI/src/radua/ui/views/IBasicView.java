package radua.ui.views;

import radua.ui.ids.ViewId;
import radua.ui.models.IBasicModel;
import radua.ui.observers.IObserver;


public interface IBasicView<MDL extends IBasicModel> extends IObserver
{
	ViewId id();
	MDL model();
}
