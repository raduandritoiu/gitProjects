package radua.ui.logic.models.tracks;

import radua.ui.logic.basics.IReadablePoint;
import radua.ui.logic.basics.IReadableSize;
import radua.ui.logic.basics.IWritablePoint;
import radua.ui.logic.basics.MColor;
import radua.ui.logic.basics.MPoint;
import radua.ui.logic.models.GeneralModel;
import radua.ui.logic.models.snaps.DirectionalSnapPoint;


public class TrackModel extends GeneralModel
{
	protected static double SCALE_FACTOR =1;
	
	protected final IWritablePoint _originalMiddle;
	protected IWritablePoint _middle;
	
	
	public TrackModel(IReadablePoint position, IReadableSize size, int pointsNo) {
		this(position, size, MColor.GREEN, pointsNo);
	}
	public TrackModel(IReadablePoint position, IReadableSize size, MColor color, int pointsNo) {
		super(position, size, true, color, MColor.BLUE, MColor.RED, pointsNo);
		_originalMiddle = new MPoint(size.width()/2, size.height()/2);
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
