package radua.ui.logic.controllers;

import radua.ui.logic.models.IBasicModel;
import radua.ui.logic.views.IBasicView;


public interface IModelViewFactory
{
	IBasicView<? extends IBasicModel> createView(IBasicModel model);
}
