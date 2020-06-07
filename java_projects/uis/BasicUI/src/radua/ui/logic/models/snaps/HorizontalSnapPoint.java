package radua.ui.logic.models.snaps;

import radua.ui.logic.basics.IReadablePoint;
import radua.ui.logic.models.SnapModel;

public class HorizontalSnapPoint extends PositionalSnapPoint
{
	public HorizontalSnapPoint(SnapModel parent, IReadablePoint pointA) { 
		this(parent, pointA.x(), pointA.y()); 
	}
	public HorizontalSnapPoint(SnapModel parent, double x, double y) {
		super(parent, x, y);
	}
	
	protected boolean acceptsTypeForSnap(ISnapPoint peerPoint) {
		return (peerPoint instanceof HorizontalSnapPoint);
	}
}
