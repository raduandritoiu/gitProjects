package radua.tracks.logic.models;

import radua.ui.logic.basics.IReadablePoint;
import radua.ui.logic.basics.MPoint;
import radua.ui.logic.models.snaps.DirectionalSnapPoint;
import radua.ui.logic.observers.ObservableProperty;
import radua.ui.logic.utils.Constants;


public class SplitTrack extends TrackModel
{
	private static final long serialVersionUID = 5835548920634804968L;
	
	
	private Direction _direction;
	
	public SplitTrack(double x, double y) {
		super(new MPoint(x, y), new MPoint(50, 50).scale(SCALE_FACTOR));
		
		_direction = Direction.LEFT;
		
		int n = 3;
		_originalPoints = new MPoint[11];
		_originalPoints[0] = new MPoint(10, 30).scale(SCALE_FACTOR);
		_originalPoints[1] = new MPoint(10, 70).scale(SCALE_FACTOR);
		_originalPoints[2] = new MPoint(10 + 8*n, 70).scale(SCALE_FACTOR);

		_originalPoints[3] = new MPoint(75 - 7*n, 87 - 4*n).scale(SCALE_FACTOR);
		_originalPoints[4] = new MPoint(75, 87).scale(SCALE_FACTOR);
		_originalPoints[5] = new MPoint(95, 53).scale(SCALE_FACTOR);

		_originalPoints[6] = new MPoint(90, 50).scale(SCALE_FACTOR);
		
		_originalPoints[7] = new MPoint(95, 47).scale(SCALE_FACTOR);
		_originalPoints[8] = new MPoint(75, 13).scale(SCALE_FACTOR);
		_originalPoints[9] = new MPoint(75 - 7*n, 13 + 4*n).scale(SCALE_FACTOR);
		
		_originalPoints[10] = new MPoint(10 + 8*n, 30).scale(SCALE_FACTOR);

		_drawPoints = new MPoint[11];
		for (int i = 0; i < _originalPoints.length; i++) {
			_drawPoints[i] = new MPoint();
		}
		updatePolygonPoints();
		
		_snapPoints.add(new DirectionalSnapPoint(this, new MPoint(10, 50).scale(SCALE_FACTOR), Constants.RAD_180));
		_snapPoints.add(new DirectionalSnapPoint(this, new MPoint(85, 70).scale(SCALE_FACTOR), Constants.RAD_30));
		_snapPoints.add(new DirectionalSnapPoint(this, new MPoint(85, 30).scale(SCALE_FACTOR), Constants.RAD_330));
	}
	
	public IReadablePoint thirdPoint() {
		return ((DirectionalSnapPoint) _snapPoints.get(2)).relativeA();
	}
	
	public Direction direction() {
		return _direction;
	}
	
	public boolean goesLeft() {
		return _direction == Direction.LEFT;
	}
	public void goLeft() {
		_direction = Direction.LEFT; 
		notifyObservers(ObservableProperty.ALL, null);
	}
	
	public boolean goesRight() {
		return _direction == Direction.RIGHT;
	}
	public void goRight() {
		_direction = Direction.RIGHT;
		notifyObservers(ObservableProperty.ALL, null);
	}
	
	public void toggleDirection() {
		if (_direction == Direction.LEFT)
			goRight();
		else
			goRight();
	}
	
	
	public DirectionalSnapPoint otherEnd(DirectionalSnapPoint crtEnd) {
		if (crtEnd == _snapPoints.get(1))
			return (DirectionalSnapPoint) _snapPoints.get(0);
		if (crtEnd == _snapPoints.get(2))
			return (DirectionalSnapPoint) _snapPoints.get(0);
		if (goesLeft())
			return (DirectionalSnapPoint) _snapPoints.get(1);
		return (DirectionalSnapPoint) _snapPoints.get(2);
	}

	
	// ===============================================================
	// ===============================================================
	
	public static enum Direction {
		LEFT,
		RIGHT;
	}
}
