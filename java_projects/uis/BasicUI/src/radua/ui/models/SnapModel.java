package radua.ui.models;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import radua.ui.common.IReadablePoint;
import radua.ui.common.IReadableSize;
import radua.ui.models.snaps.DrawSnapPoint;
import radua.ui.models.snaps.ISnapPoint;
import radua.ui.models.snaps.SnapResult;
import radua.ui.observers.ObservableEvent;
import radua.ui.utils.Constants;


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
	
	public SnapResult snaps(SnapModel remoteModel) {
		SnapResult result = SnapResult.FALSE();
		
		for (ISnapPoint localSnap : _snapPoints) {
			// cache info about last snap
			ISnapPoint lastRemoteSnap = localSnap.getSnap();
			ISnapPoint newRemoteSnap = lastRemoteSnap;
			double newSnapStrength = Constants.MAX_INF;
			if (lastRemoteSnap != null) {
    			newSnapStrength = localSnap.getSnapStength(lastRemoteSnap);
			}
			
			// find snap point with best strength snap
    		for (ISnapPoint remoteSnap : remoteModel._snapPoints) {
    			// skip last remote snap point
    			if (remoteSnap == lastRemoteSnap)
    				continue;
    			if (localSnap.canSnap(remoteSnap)) {
					double snapStrength = localSnap.getSnapStength(remoteSnap);
    				if (snapStrength < newSnapStrength) {
    					newSnapStrength = snapStrength;
    					newRemoteSnap = remoteSnap;
    				}
    			}
    		}
    		
			// update the snap
			if (newRemoteSnap != lastRemoteSnap) {
				
				String str = "============ update SNAP POINT local:"+id()+"."+localSnap.id()+
						"  new:"+newRemoteSnap.parent().id()+"."+newRemoteSnap.id()+"  last:";
				
				if (lastRemoteSnap != null) {
    				lastRemoteSnap.setSnap(null);
    				lastRemoteSnap.parent().notifyObservers(ObservableEvent.SNAP_CHANGE, false);
    				
    				str += lastRemoteSnap.parent().id()+"."+lastRemoteSnap.id();
				}
				else {
					str += "null";
				}
				System.out.println(str);
				
				localSnap.setSnap(newRemoteSnap);
				newRemoteSnap.setSnap(localSnap);
				notifyObservers(ObservableEvent.SNAP_CHANGE, true);
				newRemoteSnap.parent().notifyObservers(ObservableEvent.SNAP_CHANGE, true);
				result = SnapResult.TRUE(localSnap, newRemoteSnap);
			}
			// test if last snap still snaps
			else if (lastRemoteSnap != null) {
				if (localSnap.canSnap(lastRemoteSnap)) {
					
					System.out.println("============ keep on SNAP POINT local:"+id()+"."+localSnap.id()+
						"  last:"+lastRemoteSnap.parent().id()+"."+lastRemoteSnap.id());
					
					result = SnapResult.TRUE(localSnap, lastRemoteSnap);
    			}
    			// remove last snap
    			else {
    				
					System.out.println("============ remove SNAP POINT local:"+id()+"."+localSnap.id()+
							"  last:"+lastRemoteSnap.parent().id()+"."+lastRemoteSnap.id());
					
					localSnap.setSnap(null);
    				lastRemoteSnap.setSnap(null);
    				notifyObservers(ObservableEvent.SNAP_CHANGE, false);
    				lastRemoteSnap.parent().notifyObservers(ObservableEvent.SNAP_CHANGE, false);
    			}
			}
    		else {
    			// nothing to do, apparently even last remote snap was null
    		}
		}

		return result;
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
