package radua.ui.models.tracks;

import java.awt.Point;

import radua.ui.models.snaps.DirectionalSnapPoint;
import radua.ui.observers.ObservableEvent;
import radua.ui.utils.Rotations;


public class SplitTrack extends TrackModel
{
	private Direction _direction;
	
	
	public SplitTrack(int x, int y) {
		super(x, y);
		
		_direction = Direction.LEFT;
		
		int n = 3;
		_drawPoints = new Point[11];
		_originalDrawPoints = new Point[11];
		_originalDrawPoints[0] = new Point(10, 30);
		_originalDrawPoints[1] = new Point(10, 70);
		_originalDrawPoints[2] = new Point(10 + 8*n, 70);

		_originalDrawPoints[3] = new Point(80 - 7*n, 87 - 4*n);
		_originalDrawPoints[4] = new Point(80, 87);
		_originalDrawPoints[5] = new Point(100, 53);

		_originalDrawPoints[6] = new Point(90, 50);
		
		_originalDrawPoints[7] = new Point(100, 47);
		_originalDrawPoints[8] = new Point(80, 13);
		_originalDrawPoints[9] = new Point(80 - 7*n, 13 + 4*n);
		
		_originalDrawPoints[10] = new Point(10 + 8*n, 30);

		updatePolygonPoints();
		
		_snapPoints.add(new DirectionalSnapPoint(this, 10, 50, Rotations.RAD_180));
		_snapPoints.add(new DirectionalSnapPoint(this, 90, 70, Rotations.RAD_30));
		_snapPoints.add(new DirectionalSnapPoint(this, 90, 30, Rotations.RAD_330));
	}
	
	public Point thirdPoint() {
		return ((DirectionalSnapPoint) _snapPoints.get(2)).relativeA();
	}
	
	public Direction direction() { return _direction; }
	
	public boolean goesLeft() { return _direction == Direction.LEFT; }
	public void goLeft() {
		_direction = Direction.LEFT; 
		notifyObservers(ObservableEvent.ALL, null);
	}
	
	public boolean goesRight() { return _direction == Direction.RIGHT; }
	public void goRight() {
		_direction = Direction.RIGHT;
		notifyObservers(ObservableEvent.ALL, null);
	}
	
	
	
	public static enum Direction {
		LEFT,
		RIGHT;
	}
}
