package radua.ui.models;

import java.awt.Color;

import radua.ui.common.IReadablePoint;
import radua.ui.common.IReadableSize;
import radua.ui.common.IWritablePoint;


public class PolygonModel extends BasicModel implements IPolygonModel
{
	protected IReadablePoint[] _originalPoints;
	protected IWritablePoint[] _drawPoints;

	
//	public PolygonModel() {
//		super();
//	}
	public PolygonModel(IReadablePoint position, IReadableSize size, Color color) {
		this(position.x(), position.y(), size.width(), size.height(), color);
	}
	public PolygonModel(double x, double y, double width, double height, Color color) {
		super(x, y, width, height, color);
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
