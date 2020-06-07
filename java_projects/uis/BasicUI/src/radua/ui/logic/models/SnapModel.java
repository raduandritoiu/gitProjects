package radua.ui.logic.models;

import java.util.ArrayList;
import java.util.List;

import radua.ui.logic.basics.IReadablePoint;
import radua.ui.logic.basics.IReadableSize;
import radua.ui.logic.basics.MColor;
import radua.ui.logic.models.snaps.DrawSnapPoint;
import radua.ui.logic.models.snaps.ISnapPoint;
import radua.ui.logic.observers.ObservableProperty;


public abstract class SnapModel extends BasicModel implements ISnapModel
{
	private MColor _unsnappedColor;
	private MColor _snappedColor;
	protected final List<ISnapPoint> _snapPoints;
	
	
	public SnapModel(IReadablePoint position, IReadableSize size, boolean visible, MColor color, MColor unsnapColor, MColor snapColor) {
		this(position.x(), position.y(), size.width(), size.height(), visible, color, unsnapColor, snapColor);
	}
	public SnapModel(double x, double y, double width, double height, boolean visible, MColor color, MColor unsnapColor, MColor snapColor) {
		super(x, y, width, height, visible, color);
		_unsnappedColor = unsnapColor;
		_snappedColor = snapColor;
		_snapPoints = new ArrayList<ISnapPoint>();
	}
	
	
	public MColor getUnsnappedColor() { return _unsnappedColor; }
	public void setunsnappedColor(MColor color) { 
		MColor tmp = _unsnappedColor;
		_unsnappedColor = color;
		notifyObservers(ObservableProperty.SNAP, tmp);
	}
	
	public MColor getSnappedColor() { return _snappedColor; }
	public void setSnappedColor(MColor color) { 
		MColor tmp = _snappedColor;
		_snappedColor = color;
		notifyObservers(ObservableProperty.SNAP, tmp);
	}

	public List<ISnapPoint> snapPoints() {
		return _snapPoints;
	}
	
	public ArrayList<DrawSnapPoint> getDrawSnapPoints() {
		ArrayList<DrawSnapPoint> points = new ArrayList<>(2);
		for (ISnapPoint snapPoint : _snapPoints) {
			points.addAll(snapPoint.getDrawPoints());
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
