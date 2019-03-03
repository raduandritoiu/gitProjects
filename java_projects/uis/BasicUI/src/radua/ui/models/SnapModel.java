package radua.ui.models;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import radua.ui.common.IReadablePoint;
import radua.ui.common.IReadableSize;
import radua.ui.models.snaps.DrawSnapPoint;
import radua.ui.models.snaps.ISnapPoint;
import radua.ui.observers.ObservableEvent;


public class SnapModel extends BasicModel implements ISnapModel
{
	private Color _unsnappedColor;
	private Color _snappedColor;
	protected final List<ISnapPoint> _snapPoints;
	
	
	public SnapModel(IReadablePoint position, IReadableSize size, Color color, Color unsnapColor, Color snapColor) {
		this(position.x(), position.y(), size.width(), size.height(), color, unsnapColor, snapColor);
	}
	public SnapModel(double x, double y, double width, double height, Color color, Color unsnapColor, Color snapColor) {
		super(x, y, width, height, color);
		_unsnappedColor = unsnapColor;
		_snappedColor = snapColor;
		_snapPoints = new ArrayList<ISnapPoint>();
	}
	
	
	public Color getUnsnappedColor() { return _unsnappedColor; }
	public void setunsnappedColor(Color color) { 
		Color tmp = _unsnappedColor;
		_unsnappedColor = color;
		notifyObservers(ObservableEvent.SNAP_CHANGE, tmp);
	}
	
	public Color getSnappedColor() { return _snappedColor; }
	public void setSnappedColor(Color color) { 
		Color tmp = _snappedColor;
		_snappedColor = color;
		notifyObservers(ObservableEvent.SNAP_CHANGE, tmp);
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
