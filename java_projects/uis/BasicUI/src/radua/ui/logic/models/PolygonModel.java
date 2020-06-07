package radua.ui.logic.models;

import radua.ui.logic.basics.IReadablePoint;
import radua.ui.logic.basics.IReadableSize;
import radua.ui.logic.basics.IWritablePoint;
import radua.ui.logic.basics.MColor;


public abstract class PolygonModel extends BasicModel implements IPolygonModel
{
	protected IReadablePoint[] _originalPoints;
	protected IWritablePoint[] _drawPoints;

	
	public PolygonModel(IReadablePoint position, IReadableSize size, boolean visible, MColor color) {
		this(position.x(), position.y(), size.width(), size.height(), visible, color);
	}
	public PolygonModel(double x, double y, double width, double height, boolean visible, MColor color) {
		super(x, y, width, height, visible, color);
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
