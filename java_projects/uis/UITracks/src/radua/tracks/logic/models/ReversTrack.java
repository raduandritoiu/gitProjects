package radua.tracks.logic.models;

import java.awt.Color;

import radua.ui.logic.basics.MPoint;
import radua.ui.logic.models.snaps.DirectionalSnapPoint;
import radua.ui.logic.utils.Constants;


public class ReversTrack extends TrackModel
{
	private static final long serialVersionUID = -6333506226807182650L;
	
	
	public ReversTrack(double x, double y) {
		super(new MPoint(x, y), new MPoint(50, 50).scale(SCALE_FACTOR));
		
		_color = Color.PINK;
		
		int n = 3;
		_originalPoints = new MPoint[8];
		_originalPoints[0] = new MPoint(10, 30).scale(SCALE_FACTOR);
		_originalPoints[1] = new MPoint(10, 70).scale(SCALE_FACTOR);
		_originalPoints[2] = new MPoint(10 + 8*n, 70).scale(SCALE_FACTOR);

		_originalPoints[3] = new MPoint(95 - 7*n, 47 + 4*n).scale(SCALE_FACTOR);
		_originalPoints[4] = new MPoint(95, 47).scale(SCALE_FACTOR);
		_originalPoints[5] = new MPoint(75, 13).scale(SCALE_FACTOR);
		_originalPoints[6] = new MPoint(75 - 7*n, 13 + 4*n).scale(SCALE_FACTOR);
		
		_originalPoints[7] = new MPoint(10 + 8*n, 30).scale(SCALE_FACTOR);
		
		_drawPoints = new MPoint[8];
		for (int i = 0; i < _originalPoints.length; i++) {
			_drawPoints[i] = new MPoint();
		}

		updatePolygonPoints();
		
		_snapPoints.add(new DirectionalSnapPoint(this, new MPoint(10, 50).scale(SCALE_FACTOR), Constants.RAD_180));
		_snapPoints.add(new DirectionalSnapPoint(this, new MPoint(85, 30).scale(SCALE_FACTOR), Constants.RAD_330));
	}
	
	public DirectionalSnapPoint otherEnd(DirectionalSnapPoint crtEnd) {
		if (crtEnd == _snapPoints.get(0))
			return (DirectionalSnapPoint) _snapPoints.get(1);
		return (DirectionalSnapPoint) _snapPoints.get(0);
	}
}
