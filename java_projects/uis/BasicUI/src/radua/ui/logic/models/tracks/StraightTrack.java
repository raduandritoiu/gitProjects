package radua.ui.logic.models.tracks;

import radua.ui.logic.basics.MPoint;
import radua.ui.logic.models.snaps.DirectionalSnapPoint;
import radua.ui.logic.utils.Constants;


public class StraightTrack extends TrackModel
{
	public StraightTrack(int x, int y) {
		super(new MPoint(x, y), new MPoint(50, 50).scale(SCALE_FACTOR));
		_originalPoints = new MPoint[4];
		_originalPoints[0] = new MPoint(10, 30).scale(SCALE_FACTOR);
		_originalPoints[1] = new MPoint(10, 70).scale(SCALE_FACTOR);
		_originalPoints[2] = new MPoint(85, 70).scale(SCALE_FACTOR);
		_originalPoints[3] = new MPoint(85, 30).scale(SCALE_FACTOR);
		
		_drawPoints = new MPoint[4];
		for (int i = 0; i < _originalPoints.length; i++) {
			_drawPoints[i] = new MPoint();
		}
		updatePolygonPoints();
		
		_snapPoints.add(new DirectionalSnapPoint(this, new MPoint(10, 50).scale(SCALE_FACTOR), Constants.RAD_180));
		_snapPoints.add(new DirectionalSnapPoint(this, new MPoint(85, 50).scale(SCALE_FACTOR), 0));
	}
}
