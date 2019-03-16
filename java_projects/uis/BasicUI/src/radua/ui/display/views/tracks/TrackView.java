package radua.ui.display.views.tracks;

import radua.ui.display.views.GeneralView;
import radua.ui.display.views.painters.tracks.TrackPainter;
import radua.ui.logic.models.tracks.TrackModel;


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
