package radua.ui.models.tracks;

import java.awt.Point;

import radua.ui.models.snaps.DirectionalSnapPoint;
import radua.ui.utils.Rotations;


public class CurvedTrack extends TrackModel
{
	public CurvedTrack(int x, int y) {
		super(x, y);
		
		int n = 3;
		_drawPoints = new Point[8];
		_originalDrawPoints = new Point[8];
		
		_originalDrawPoints[0] = new Point(10, 30);
		_originalDrawPoints[1] = new Point(10, 70);
		_originalDrawPoints[2] = new Point(10 + 8*n, 70);

		_originalDrawPoints[3] = new Point(80 - 7*n, 87 - 4*n);
		_originalDrawPoints[4] = new Point(80, 87);
		_originalDrawPoints[5] = new Point(100, 53);
		_originalDrawPoints[6] = new Point(100 - 7*n, 53 - 4*n);
		
		_originalDrawPoints[7] = new Point(10 + 8*n, 30);
		
		updatePolygonPoints();
		
		_snapPoints.add(new DirectionalSnapPoint(this, 10, 50, Rotations.RAD_180));
		_snapPoints.add(new DirectionalSnapPoint(this, 90, 70, Rotations.RAD_30));
	}
	
}
