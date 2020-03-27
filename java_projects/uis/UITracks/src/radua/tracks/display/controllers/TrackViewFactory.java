package radua.tracks.display.controllers;

import radua.tracks.display.views.TrackView;
import radua.tracks.logic.models.TrackModel;
import radua.tracks.logic.models.Train;
import radua.ui.display.controllers.ModelViewFactory;
import radua.ui.display.views.OvalView;
import radua.ui.logic.models.IBasicModel;
import radua.ui.logic.views.IBasicView;


public class TrackViewFactory extends ModelViewFactory 
{
	@Override
	public IBasicView<? extends IBasicModel> createView(IBasicModel model) {
		if (model instanceof TrackModel) {
			return new TrackView((TrackModel) model);
		}
		
		if (model instanceof Train) {
			return new OvalView(model);
		}

		return super.createView(model);
	}
}
