package radua.ui.views.shapes;

import radua.ui.display.painters.tracks.TrackPainter;
import radua.ui.logic.models.tracks.TrackModel;
import radua.ui.views.GeneralView;


public class TrackView extends GeneralView<TrackModel>
{
	private static final long serialVersionUID = -5859261874731412601L;
	

	public TrackView(TrackModel model) {
		super(model);
	}

	
	/** add the painter before the super ones */
	@Override
	protected void addInitialPainters() {
		super.addInitialPainters();
		_painters.add(TrackPainter.Instance);
	}
}
