package radua.ui.logic.models;

import java.awt.Color;

import radua.ui.logic.basics.IReadablePoint;
import radua.ui.logic.basics.IReadableSize;
import radua.ui.logic.basics.IWritablePoint;


public class GeneralModel extends SnapModel implements IGeneralModel
{
	protected IReadablePoint[] _originalPoints;
	protected IWritablePoint[] _drawPoints;

	
	public GeneralModel(IReadablePoint position, IReadableSize size, Color color, Color unsnapColor, Color snapColor, boolean visible) {
		this(position.x(), position.y(), size.width(), size.height(), color, unsnapColor, snapColor, visible);
	}
	public GeneralModel(double x, double y, double width, double height, Color color, Color unsnapColor, Color snapColor, boolean visible) {
		super(x, y, width, height, color, unsnapColor, snapColor, visible);
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
