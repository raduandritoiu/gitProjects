package radua.ui.models.tracks;

import java.awt.Point;

import radua.ui.models.GeneralModel;
import radua.ui.models.snaps.DirectionalSnapPoint;


public class TrackModel extends GeneralModel
{
	protected final Point _originalMiddle;
	protected Point _middle;
	
	public TrackModel(int x, int y) {
		this(x, y, new Point(50, 50));
	}
	
	public TrackModel(int x, int y, Point middle) {
		super(x, y);
		_originalMiddle = middle;
		_middle = relativePoint(_originalMiddle);
	}
	
	
	public Point firstPoint() {
		return ((DirectionalSnapPoint) _snapPoints.get(0)).relativeA();
	}
	
	public Point middlePoint() {
		return _middle;
	}
	
	public Point secondPoint() {
		return ((DirectionalSnapPoint) _snapPoints.get(1)).relativeA();
	}
	
	
	@Override
	protected void rotateLogic(double oldRotation) {
		_middle = relativePoint(_originalMiddle);
		super.rotateLogic(oldRotation);
	}
}
