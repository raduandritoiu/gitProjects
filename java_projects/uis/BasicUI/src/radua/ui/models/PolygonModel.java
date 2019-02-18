package radua.ui.models;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;


public class PolygonModel extends BasicModel implements IPolygonModel
{
	protected Point[] _originalDrawPoints;
	protected Point[] _drawPoints;

	public PolygonModel(int x, int y) {
		super(x, y, 100, 100, Color.GREEN);
	}
	
	protected void updatePolygonPoints() {
		for (int i = 0; i < _originalDrawPoints.length; i++) {
			_drawPoints[i] = relativePoint(_originalDrawPoints[i]);
		}
	}
	
	@Override
	protected void resizeLogic(Dimension oldDimension) {
		updatePolygonPoints();
		super.resizeLogic(oldDimension);
	}
	
	@Override
	protected void rotateLogic(double oldRotation) {
		updatePolygonPoints();
		super.rotateLogic(oldRotation);
	}
	
	public Point[] getPolygonPoints() {
		return _drawPoints;
	}
}
