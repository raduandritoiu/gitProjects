package radua.ui.logic.models.snaps;

import radua.ui.logic.basics.IReadablePoint;
import radua.ui.logic.basics.IReadableSize;
import radua.ui.logic.ids.IdManager;
import radua.ui.logic.ids.SnapId;
import radua.ui.logic.models.SnapModel;

public abstract class BasicSnapPoint implements ISnapPoint
{
	protected final SnapId _id;
	protected final SnapModel _parent;
	protected ISnapPoint _peer;
	
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
	
	
	public void setSnapPeer(ISnapPoint model) {
		_peer = model;
	}
	public ISnapPoint getSnapPeer() {
		return _peer;
	}
	public boolean isSnapped() {
		return _peer != null;
	}
	
	
	public void parentMoved(IReadablePoint oldPosition) { updatePoints(); }
	public void parentResized(IReadableSize oldSize) { updatePoints(); }
	public void parentRotated(double oldRotation) { updatePoints(); }
	protected abstract void updatePoints();
	
	
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
