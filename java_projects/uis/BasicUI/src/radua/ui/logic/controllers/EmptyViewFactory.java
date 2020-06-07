package radua.ui.logic.controllers;

import radua.ui.logic.models.IBasicModel;
import radua.ui.logic.view.IModelView;

public class EmptyViewFactory implements IModelViewFactory
{
	public IModelView<? extends IBasicModel> createView(IBasicModel model) {
		return null;
	}
}
