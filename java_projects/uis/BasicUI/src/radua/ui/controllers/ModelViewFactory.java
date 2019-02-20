package radua.ui.controllers;

import radua.ui.models.IBasicModel;
import radua.ui.views.BasicView;
import radua.ui.views.RectView;

public class ModelViewFactory 
{
	public BasicView<IBasicModel> createView(IBasicModel model) {
		return new RectView(model);
	}
}
