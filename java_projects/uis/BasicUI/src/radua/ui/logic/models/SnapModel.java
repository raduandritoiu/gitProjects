package radua.ui.logic.models;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import radua.ui.logic.basics.IReadablePoint;
import radua.ui.logic.basics.IReadableSize;
import radua.ui.logic.models.snaps.DrawSnapPoint;
import radua.ui.logic.models.snaps.ISnapPoint;
import radua.ui.logic.observers.ObservableProperty;


public class SnapModel extends BasicModel implements ISnapModel
{
	private Color _unsnappedColor;
	private Color _snappedColor;
	protected final List<ISnapPoint> _snapPoints;
	
	
	public SnapModel(IReadablePoint position, IReadableSize size, Color color, Color unsnapColor, Color snapColor, boolean visible) {
		this(position.x(), position.y(), size.width(), size.height(), color, unsnapColor, snapColor, visible);
	}
	public SnapModel(double x, double y, double width, double height, Color color, Color unsnapColor, Color snapColor, boolean visible) {
		super(x, y, width, height, color, visible);
		_unsnappedColor = unsnapColor;
		_snappedColor = snapColor;
		_snapPoints = new ArrayList<ISnapPoint>();
	}
	
	
	public Color getUnsnappedColor() { return _unsnappedColor; }
	public void setunsnappedColor(Color color) { 
		Color tmp = _unsnappedColor;
		_unsnappedColor = color;
		notifyObservers(ObservableProperty.SNAP, tmp);
	}
	
	public Color getSnappedColor() { return _snappedColor; }
	public void setSnappedColor(Color color) { 
		Color tmp = _snappedColor;
		_snappedColor = color;
		notifyObservers(ObservableProperty.SNAP, tmp);
	}

	public List<ISnapPoint> snapPoints() {
		return _snapPoints;
	}
	
	public ArrayList<DrawSnapPoint> getDrawSnapPoints() {
		ArrayList<DrawSnapPoint> points = new ArrayList<>(2);
		for (ISnapPoint snappingPoint : _snapPoints) {
			points.addAll(snappingPoint.getDrawPoints());
		}
		return points;
	}
	
	
	@Override
	protected void moveLogic(IReadablePoint oldPosition) {
		for (ISnapPoint snappingPoint : _snapPoints) {
			snappingPoint.parentMoved(oldPosition);
		}
	}
	@Override
	protected void resizeLogic(IReadableSize oldSize) {
		for (ISnapPoint snappingPoint : _snapPoints) {
			snappingPoint.parentResized(oldSize);
		}
	}
	@Override
	protected void rotateLogic(double oldRotation) {
		for (ISnapPoint snappingPoint : _snapPoints) {
			snappingPoint.parentRotated(oldRotation);
		}
	}
	
	
	@Override
	public String debugString(int tabs) {
		String str = super.debugString(tabs) + "\n";
		for (int i = 0; i < tabs; i++) str += "\t";
		
		boolean first = true;
		for (ISnapPoint snap : _snapPoints) {
			if (first)
				str += snap.debugString(tabs + 1);
			else
				str += "\n" + snap.debugString(tabs + 1);
			first = false;
		}
		
		return str;
	}
}
