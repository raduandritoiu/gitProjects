package radua.ui.models.snaps;

import radua.ui.common.IReadablePoint;
import radua.ui.common.IReadableSize;
import radua.ui.ids.IdManager;
import radua.ui.ids.SnapId;
import radua.ui.models.SnapModel;

public abstract class BasicSnapPoint implements ISnapPoint
{
	protected final SnapId _id;
	protected final SnapModel _parent;
	protected ISnapPoint _neighbour;
	
	public BasicSnapPoint(SnapModel parent) {
		_id = IdManager.GetSnapId();
		_parent = parent;
	}
	public SnapId id() {
		return _id;
	}
	public SnapModel parent() {
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
	
	public void parentMoved(IReadablePoint oldPosition) { updatePoints(); }
	public void parentResized(IReadableSize oldSize) { updatePoints(); }
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
