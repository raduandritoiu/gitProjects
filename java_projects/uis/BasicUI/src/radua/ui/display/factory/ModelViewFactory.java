package radua.ui.display.factory;

import radua.ui.logic.controllers.IModelViewFactory;
import radua.ui.logic.models.IBasicModel;
import radua.ui.logic.models.IGeneralModel;
import radua.ui.logic.models.shapes.CircleSnapModel;
import radua.ui.logic.models.shapes.OvalModel;
import radua.ui.logic.models.shapes.OvalSnapModel;
import radua.ui.logic.models.shapes.RectModel;
import radua.ui.logic.models.shapes.RectSnapModel;
import radua.ui.logic.models.shapes.SquareSnapModel;
import radua.ui.logic.models.tracks.TrackModel;
import radua.ui.logic.view.IModelView;
import radua.ui.views.GeneralView;
import radua.ui.views.shapes.OvalSnapView;
import radua.ui.views.shapes.OvalView;
import radua.ui.views.shapes.RectSnapView;
import radua.ui.views.shapes.RectView;
import radua.ui.views.shapes.TrackView;


public class ModelViewFactory implements IModelViewFactory
{
	public IModelView<? extends IBasicModel> createView(IBasicModel model) {
		if (model instanceof TrackModel) { return new TrackView((TrackModel) model); }
		
		if (model instanceof RectModel) { return new RectView((RectModel) model); }
		if (model instanceof OvalModel) { return new OvalView((OvalModel) model); }
		if (model instanceof RectSnapModel) { return new RectSnapView((RectSnapModel) model); }
		if (model instanceof SquareSnapModel) { return new RectSnapView((SquareSnapModel) model); }
		if (model instanceof OvalSnapModel) { return new OvalSnapView((OvalSnapModel) model); }
		if (model instanceof CircleSnapModel) { return new OvalSnapView((CircleSnapModel) model); }
		
		
		if (model instanceof IGeneralModel) { return new GeneralView<IGeneralModel>((IGeneralModel) model); }
		
		return null;
	}
}
