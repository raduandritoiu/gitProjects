package radua.ui.logic.models.tracks;

import radua.ui.logic.basics.MColor;
import radua.ui.logic.basics.MPoint;
import radua.ui.logic.basics.MSize;
import radua.ui.logic.models.snaps.DirectionalSnapPoint;
import radua.ui.logic.utils.Constants;


public class DescendTrack extends TrackModel
{
	public DescendTrack(int x, int y) {
		super(new MPoint(x, y), new MSize(340, 340).scale(SCALE_FACTOR), MColor.CYAN, 4);
		_originalPoints[0] = new MPoint(10, 150).scale(SCALE_FACTOR);
		_originalPoints[1] = new MPoint(10, 190).scale(SCALE_FACTOR);
		_originalPoints[2] = new MPoint(330, 190).scale(SCALE_FACTOR);
		_originalPoints[3] = new MPoint(330, 150).scale(SCALE_FACTOR);
		
		for (int i = 0; i < _originalPoints.length; i++) {
			_drawPoints[i] = new MPoint();
		}
		updatePolygonPoints();
		
		_snapPoints.add(new DirectionalSnapPoint(this, new MPoint(10, 170).scale(SCALE_FACTOR), Constants.RAD_180));
		_snapPoints.add(new DirectionalSnapPoint(this, new MPoint(330, 170).scale(SCALE_FACTOR), 0));
	}
}
