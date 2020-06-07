package radua.ui.logic.controllers;

import radua.ui.logic.models.IBasicModel;
import radua.ui.logic.view.IModelView;


public interface IModelViewFactory
{
	IModelView<? extends IBasicModel> createView(IBasicModel model);
}
