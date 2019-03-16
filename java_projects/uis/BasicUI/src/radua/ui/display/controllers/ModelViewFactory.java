package radua.ui.display.controllers;

import radua.ui.display.views.GeneralView;
import radua.ui.display.views.RectSnapView;
import radua.ui.display.views.RectView;
import radua.ui.display.views.tracks.TrackView;
import radua.ui.logic.controllers.IModelViewFactory;
import radua.ui.logic.models.IBasicModel;
import radua.ui.logic.models.IGeneralModel;
import radua.ui.logic.models.ISnapModel;
import radua.ui.logic.models.tracks.TrackModel;
import radua.ui.logic.views.IBasicView;


public class ModelViewFactory implements IModelViewFactory
{
	public IBasicView<? extends IBasicModel> createView(IBasicModel model) {
		if (model instanceof TrackModel) {
			return new TrackView((TrackModel) model);
		}
		
		if (model instanceof IGeneralModel) {
			return new GeneralView<IGeneralModel>((IGeneralModel) model);
		}
		
		if (model instanceof ISnapModel) {
			return new RectSnapView((ISnapModel) model);
		}
		
		return new RectView(model);
	}
}
