package radua.ui.models.snaps;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import radua.ui.models.SnapModel;

public interface ISnapPoint 
{
	SnapModel getParent();
	void parentMoved(Point oldPosition);
	void parentResized(Dimension oldDimension);
	void parentRotated(double oldRotation);
	
	void setSnap(ISnapPoint snapPoint);
	ISnapPoint getSnap();
	boolean isSnapped();
	boolean canSnap(ISnapPoint snappingPoint);
	SnapResultMove getSnapMove(ISnapPoint snappingPoint);
	
	ArrayList<DrawSnapPoint> getDrawPoints();
	
	public String debugString(int tabs);
}
