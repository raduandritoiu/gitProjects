package radua.ui.logic.models.snaps;

import java.util.ArrayList;

import radua.ui.logic.basics.IReadablePoint;
import radua.ui.logic.basics.IReadableSize;
import radua.ui.logic.ids.SnapId;
import radua.ui.logic.models.SnapModel;

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
