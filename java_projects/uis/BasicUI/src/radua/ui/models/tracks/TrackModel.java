package radua.ui.models.tracks;

import java.awt.Color;

import radua.ui.common.IReadablePoint;
import radua.ui.common.IWritablePoint;
import radua.ui.common.MPoint;
import radua.ui.common.MSize;
import radua.ui.models.GeneralModel;
import radua.ui.models.snaps.DirectionalSnapPoint;


public class TrackModel extends GeneralModel
{
	protected static double SCALE_FACTOR =1;
	
	protected final IWritablePoint _originalMiddle;
	protected IWritablePoint _middle;
	
	
	public TrackModel(IReadablePoint position, IReadablePoint middle) {
		super(position, new MSize(100, 100).scale(SCALE_FACTOR), Color.GREEN, Color.BLUE, Color.RED);
		_originalMiddle = new MPoint(middle.x(), middle.y());
		_middle = new MPoint();
		relativePoint(_originalMiddle, _middle);
	}
	
	
	public IReadablePoint firstPoint() {
		return ((DirectionalSnapPoint) _snapPoints.get(0)).relativeA();
	}
	
	public IReadablePoint middlePoint() {
		return _middle;
	}
	
	public IReadablePoint secondPoint() {
		return ((DirectionalSnapPoint) _snapPoints.get(1)).relativeA();
	}
	
	
	@Override
	protected void rotateLogic(double oldRotation) {
		relativePoint(_originalMiddle, _middle);
		super.rotateLogic(oldRotation);
	}
}
