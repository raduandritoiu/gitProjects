package radua.ui.models.tracks;

import radua.ui.common.MPoint;
import radua.ui.models.snaps.DirectionalSnapPoint;
import radua.ui.utils.Constants;


public class CurvedTrack extends TrackModel
{
	public CurvedTrack(double x, double y) {
		super(new MPoint(x, y), new MPoint(50, 50).moveBy(0, 0).scale(SCALE_FACTOR));
		
		int n = 3;
		_originalPoints = new MPoint[8];
		
		_originalPoints[0] = new MPoint(10, 30).moveBy(0, 0).scale(SCALE_FACTOR);
		_originalPoints[1] = new MPoint(10, 70).moveBy(0, 0).scale(SCALE_FACTOR);
		_originalPoints[2] = new MPoint(10 + 8*n, 70).moveBy(0, 0).scale(SCALE_FACTOR);

		_originalPoints[3] = new MPoint(75 - 7*n, 87 - 4*n).moveBy(0, 0).scale(SCALE_FACTOR);
		_originalPoints[4] = new MPoint(75, 87).moveBy(0, 0).scale(SCALE_FACTOR);
		_originalPoints[5] = new MPoint(95, 53).moveBy(0, 0).scale(SCALE_FACTOR);
		_originalPoints[6] = new MPoint(95 - 7*n, 53 - 4*n).moveBy(0, 0).scale(SCALE_FACTOR);
		
		_originalPoints[7] = new MPoint(10 + 8*n, 30).moveBy(0, 0).scale(SCALE_FACTOR);
		
		_drawPoints = new MPoint[8];
		for (int i = 0; i < _originalPoints.length; i++) {
			_drawPoints[i] = new MPoint();
		}
		updatePolygonPoints();
		
		_snapPoints.add(new DirectionalSnapPoint(this, new MPoint(10, 50).moveBy(0, 0).scale(SCALE_FACTOR), Constants.RAD_180));
//		_snapPoints.add(new DirectionalSnapPoint(this, new MPoint(90, 70).translate(0, 0).scale(SCALE_FACTOR), Rotations.RAD_30));
		
		_snapPoints.add(new DirectionalSnapPoint(this, new MPoint(85, 70).moveBy(0, 0).scale(SCALE_FACTOR), Constants.RAD_30));
	}
	
}
