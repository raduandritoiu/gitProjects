package radua.ui.models.snaps;

import java.awt.Dimension;
import java.awt.Point;

import radua.ui.models.SnapModel;

public abstract class BasicSnapPoint implements ISnapPoint
{
	protected final SnapModel _parent;
	protected ISnapPoint _neighbour;
	
	public BasicSnapPoint(SnapModel parent) {
		_parent = parent;
	}
	public SnapModel getParent() {
		return _parent;
	}
	
	public void setSnap(ISnapPoint model) {
		_neighbour = model;
	}
	public ISnapPoint getSnap() {
		return _neighbour;
	}
	public boolean isSnapped() {
		return _neighbour != null;
	}
	
	public void parentMoved(Point oldPosition) { updatePoints(); }
	public void parentResized(Dimension oldDimension) { updatePoints(); }
	public void parentRotated(double oldRotation) { updatePoints(); }
	abstract void updatePoints();
	
	@Override
	public String toString() {
		return debugString(0);
	}
	public String debugString(int tabs) {
		String str = "";
		for (int i = 0; i < tabs; i++) str += "\t";
		str += super.toString();
		return str;
	}
}
