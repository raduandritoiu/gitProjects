package radua.tracks.display.views;

import radua.tracks.display.views.painters.TrackPainter;
import radua.tracks.logic.models.TrackModel;
import radua.ui.display.views.GeneralView;


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
