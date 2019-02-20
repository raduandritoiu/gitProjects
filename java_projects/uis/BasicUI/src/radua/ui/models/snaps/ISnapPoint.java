package radua.ui.models.snaps;

import java.util.ArrayList;

import radua.ui.common.IReadablePoint;
import radua.ui.common.IReadableSize;
import radua.ui.ids.SnapId;
import radua.ui.models.SnapModel;

public interface ISnapPoint 
{
	SnapId id();
	SnapModel parent();
	void parentMoved(IReadablePoint oldPosition);
	void parentResized(IReadableSize oldSize);
	void parentRotated(double oldRotation);
	
	void setSnap(ISnapPoint snapPoint);
	ISnapPoint getSnap();
	boolean isSnapped();
	boolean canSnap(ISnapPoint snappingPoint);
	public double getSnapStength(ISnapPoint snapPoint);
	SnapResultMove getSnapMove(ISnapPoint snappingPoint);
	
	ArrayList<DrawSnapPoint> getDrawPoints();
	
	public String debugString(int tabs);
}
