package radua.ui.logic.models.snaps;

import radua.ui.logic.basics.IReadablePoint;
import radua.ui.logic.models.SnapModel;

public class VerticalSnapPoint extends PositionalSnapPoint
{
	public VerticalSnapPoint(SnapModel parent, IReadablePoint pointA) { 
		this(parent, pointA.x(), pointA.y()); 
	}
	public VerticalSnapPoint(SnapModel parent, double x, double y) {
		super(parent, x, y);
	}
	
	protected boolean acceptsTypeForSnap(ISnapPoint peerPoint) {
		return (peerPoint instanceof VerticalSnapPoint);
	}
}
