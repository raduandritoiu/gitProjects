package radua.ui.logic.views;

import radua.ui.logic.ids.ViewId;
import radua.ui.logic.models.IBasicModel;
import radua.ui.logic.observers.IObserver;


public interface IBasicView<MDL extends IBasicModel> extends IObserver
{
	ViewId id();
	MDL model();
}
