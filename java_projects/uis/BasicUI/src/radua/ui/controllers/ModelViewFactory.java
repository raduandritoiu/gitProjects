package radua.ui.controllers;

import radua.ui.models.IBasicModel;
import radua.ui.views.IBasicView;
import radua.ui.views.RectView;

public class ModelViewFactory 
{
	public IBasicView<IBasicModel> createView(IBasicModel model) {
		return new RectView(model);
	}
}
