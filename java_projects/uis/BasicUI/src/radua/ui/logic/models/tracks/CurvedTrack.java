package radua.ui.logic.models.tracks;

import radua.ui.logic.basics.MPoint;
import radua.ui.logic.basics.MSize;
import radua.ui.logic.models.snaps.DirectionalSnapPoint;
import radua.ui.logic.utils.Constants;


public class CurvedTrack extends TrackModel
{
	public CurvedTrack(double x, double y) {
		super(new MPoint(x, y), new MSize(100, 100).scale(SCALE_FACTOR), 8);
		
		int n = 3;
		_originalPoints[0] = new MPoint(10, 30).moveBy(0, 0).scale(SCALE_FACTOR);
		_originalPoints[1] = new MPoint(10, 70).moveBy(0, 0).scale(SCALE_FACTOR);
		_originalPoints[2] = new MPoint(10 + 8*n, 70).moveBy(0, 0).scale(SCALE_FACTOR);

		_originalPoints[3] = new MPoint(75 - 7*n, 87 - 4*n).moveBy(0, 0).scale(SCALE_FACTOR);
		_originalPoints[4] = new MPoint(75, 87).moveBy(0, 0).scale(SCALE_FACTOR);
		_originalPoints[5] = new MPoint(95, 53).moveBy(0, 0).scale(SCALE_FACTOR);
		_originalPoints[6] = new MPoint(95 - 7*n, 53 - 4*n).moveBy(0, 0).scale(SCALE_FACTOR);
		
		_originalPoints[7] = new MPoint(10 + 8*n, 30).moveBy(0, 0).scale(SCALE_FACTOR);
		
		for (int i = 0; i < _originalPoints.length; i++) {
			_drawPoints[i] = new MPoint();
		}
		updatePolygonPoints();
		
		_snapPoints.add(new DirectionalSnapPoint(this, new MPoint(10, 50).moveBy(0, 0).scale(SCALE_FACTOR), Constants.RAD_180));
//		_snapPoints.add(new DirectionalSnapPoint(this, new MPoint(90, 70).translate(0, 0).scale(SCALE_FACTOR), Rotations.RAD_30));
		
		_snapPoints.add(new DirectionalSnapPoint(this, new MPoint(85, 70).moveBy(0, 0).scale(SCALE_FACTOR), Constants.RAD_30));
	}
	
}
