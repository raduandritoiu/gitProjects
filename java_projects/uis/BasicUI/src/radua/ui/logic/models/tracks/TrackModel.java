package radua.ui.logic.models.tracks;

import java.awt.Color;

import radua.ui.logic.basics.IReadablePoint;
import radua.ui.logic.basics.IWritablePoint;
import radua.ui.logic.basics.MPoint;
import radua.ui.logic.basics.MSize;
import radua.ui.logic.models.GeneralModel;
import radua.ui.logic.models.snaps.DirectionalSnapPoint;


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
