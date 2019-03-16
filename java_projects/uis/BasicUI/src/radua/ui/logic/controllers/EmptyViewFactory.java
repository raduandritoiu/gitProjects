package radua.ui.logic.controllers;

import radua.ui.logic.models.IBasicModel;
import radua.ui.logic.views.IBasicView;

public class EmptyViewFactory implements IModelViewFactory
{
	public IBasicView<? extends IBasicModel> createView(IBasicModel model) {
		return null;
	}
}
