package radua.ui.models.tracks;

import java.awt.Point;

import radua.ui.models.snaps.DirectionalSnapPoint;
import radua.ui.utils.Rotations;


public class StraightTrack extends TrackModel
{
	public StraightTrack(int x, int y) {
		super(x, y);
		_drawPoints = new Point[4];
		_originalDrawPoints = new Point[4];
		_originalDrawPoints[0] = new Point(10, 30);
		_originalDrawPoints[1] = new Point(10, 70);
		_originalDrawPoints[2] = new Point(90, 70);
		_originalDrawPoints[3] = new Point(90, 30);
		updatePolygonPoints();
		
		_snapPoints.add(new DirectionalSnapPoint(this, 10, 50, Rotations.RAD_180));
		_snapPoints.add(new DirectionalSnapPoint(this, 90, 50, 0));
	}
}
