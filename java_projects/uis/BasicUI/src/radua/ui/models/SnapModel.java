package radua.ui.models;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import radua.ui.models.snaps.DrawSnapPoint;
import radua.ui.models.snaps.ISnapPoint;
import radua.ui.models.snaps.SnapResult;
import radua.ui.observers.ObservableEvent;


public class SnapModel extends BasicModel implements ISnapModel
{
	private Color _unsnappedColor;
	private Color _snappedColor;
	protected final List<ISnapPoint> _snapPoints;
	
	
	public SnapModel(int x, int y, int width, int height, Color color) { this(x, y, width, height, color, Color.BLUE, Color.RED);  }
	public SnapModel(int x, int y, int width, int height, Color color, Color unsnapColor, Color snapColor) {
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

	public ArrayList<DrawSnapPoint> getDrawSnapPoints() {
		ArrayList<DrawSnapPoint> points = new ArrayList<>(2);
		for (ISnapPoint snappingPoint : _snapPoints) {
			points.addAll(snappingPoint.getDrawPoints());
		}
		return points;
	}
	
	
	@Override
	protected void moveLogic(Point oldPosition) {
		for (ISnapPoint snappingPoint : _snapPoints) {
			snappingPoint.parentMoved(oldPosition);
		}
	}
	@Override
	protected void resizeLogic(Dimension oldDimension) {
		for (ISnapPoint snappingPoint : _snapPoints) {
			snappingPoint.parentResized(oldDimension);
		}
	}
	@Override
	protected void rotateLogic(double oldRotation) {
		for (ISnapPoint snappingPoint : _snapPoints) {
			snappingPoint.parentRotated(oldRotation);
		}
	}
	
	
	public SnapResult snaps(SnapModel remoteSnapModel) {
		for (ISnapPoint localSnapPoint : _snapPoints) {
    		for (ISnapPoint remoteSnapPoint : remoteSnapModel._snapPoints) {
    			boolean wasSnapped = localSnapPoint.isSnapped();
    			ISnapPoint lastRemoteSnapPoint = localSnapPoint.getSnap();
    			if (localSnapPoint.canSnap(remoteSnapPoint)) {
    				localSnapPoint.setSnap(remoteSnapPoint);
    				remoteSnapPoint.setSnap(localSnapPoint);
    				notifyObservers(ObservableEvent.SNAP_CHANGE, true);
    				remoteSnapModel.notifyObservers(ObservableEvent.SNAP_CHANGE, true);
    				return SnapResult.TRUE(localSnapPoint, remoteSnapPoint);
    			}
    			else if (wasSnapped) {
    				localSnapPoint.setSnap(null);
    				lastRemoteSnapPoint.setSnap(null);
    				notifyObservers(ObservableEvent.SNAP_CHANGE, false);
    				lastRemoteSnapPoint.getParent().notifyObservers(ObservableEvent.SNAP_CHANGE, false);
    			}
    		}
		}

		return SnapResult.FALSE();
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
