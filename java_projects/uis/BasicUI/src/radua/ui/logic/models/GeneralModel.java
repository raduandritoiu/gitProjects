package radua.ui.logic.models;

import radua.ui.logic.basics.IReadablePoint;
import radua.ui.logic.basics.IReadableSize;
import radua.ui.logic.basics.IWritablePoint;
import radua.ui.logic.basics.MColor;


public abstract class GeneralModel extends SnapModel implements IGeneralModel
{
	protected final IReadablePoint[] _originalPoints;
	protected final IWritablePoint[] _drawPoints;

	
	public GeneralModel(IReadablePoint position, IReadableSize size, boolean visible, MColor color, MColor unsnapColor, MColor snapColor, int pointsNo) {
		this(position.x(), position.y(), size.width(), size.height(), visible, color, unsnapColor, snapColor, pointsNo);
	}
	public GeneralModel(double x, double y, double width, double height, boolean visible, MColor color, MColor unsnapColor, MColor snapColor, int pointsNo) {
		super(x, y, width, height, visible, color, unsnapColor, snapColor);
		_originalPoints = new IReadablePoint[pointsNo];
		_drawPoints = new IWritablePoint[pointsNo];
	}
	
	
	protected void updatePolygonPoints() {
		for (int i = 0; i < _originalPoints.length; i++) {
			relativePoint(_originalPoints[i], _drawPoints[i]);
		}
	}
	
	@Override
	protected void resizeLogic(IReadableSize oldSize) {
		updatePolygonPoints();
		super.resizeLogic(oldSize);
	}
	
	@Override
	protected void rotateLogic(double oldRotation) {
		updatePolygonPoints();
		super.rotateLogic(oldRotation);
	}
	
	public IReadablePoint[] getPolygonPoints() {
		return _drawPoints;
	}
}
