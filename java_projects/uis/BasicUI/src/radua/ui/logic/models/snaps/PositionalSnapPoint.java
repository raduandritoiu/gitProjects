package radua.ui.logic.models.snaps;

import java.util.ArrayList;

import radua.ui.logic.basics.IReadablePoint;
import radua.ui.logic.basics.IWritablePoint;
import radua.ui.logic.basics.MPoint;
import radua.ui.logic.models.SnapModel;
import radua.ui.logic.utils.Calculus;
import radua.ui.logic.utils.Constants;

public class PositionalSnapPoint extends BasicSnapPoint
{
	protected double DELTA_SNAP = 30;
	
	final IWritablePoint A;
	final IWritablePoint relativeA;
	final IWritablePoint absoluteA;
	
	
	public PositionalSnapPoint(SnapModel parent, IReadablePoint pointA) { 
		this(parent, pointA.x(), pointA.y()); 
	}
	public PositionalSnapPoint(SnapModel parent, double x, double y) {
		super(parent);
		A = new MPoint(x, y);
		relativeA = new MPoint(x, y);
		absoluteA = new MPoint(x, y);
		updatePoints();
	}
	
	
	public IReadablePoint A() { return A; }
	public IReadablePoint relativeA() { return relativeA; }
	public IReadablePoint absoluteA() { return absoluteA; }

	
	protected void updatePoints() {
		_parent.relativePoint(A, relativeA);
		absoluteA.moveTo(relativeA);
		absoluteA.moveBy(_parent.position());
	}
	
	public ArrayList<DrawSnapPoint> getDrawPoints() {
		ArrayList<DrawSnapPoint> points = new ArrayList<>(2);
		points.add(new DrawSnapPoint(relativeA.intX(), relativeA.intY(), isSnapped()));
		return points;
	}

	
	protected boolean acceptsTypeForSnap(ISnapPoint peerPoint) {
		return (peerPoint instanceof PositionalSnapPoint);
	}
	
	public boolean canSnap(ISnapPoint peerPoint) {
		if (peerPoint.getSnapPeer() != null && peerPoint.getSnapPeer() != this)
			return false;
		if (!acceptsTypeForSnap(peerPoint))
			return false;
		
		PositionalSnapPoint peer = (PositionalSnapPoint) peerPoint;
		double diff = absoluteA.x() - peer.absoluteA.x();
		if (diff > 0 && diff > DELTA_SNAP)
			return false;
		if (diff < 0 && diff < -DELTA_SNAP)
			return false;
		
		diff = absoluteA.y() - peer.absoluteA.y();
		if (diff > 0 && diff > DELTA_SNAP)
			return false;
		if (diff < 0 && diff < -DELTA_SNAP)
			return false;
		return true;
	}
	
	public double getSnapStength(ISnapPoint peerPoint) {
		if (!acceptsTypeForSnap(peerPoint))
			return Constants.MAX_INF;
		PositionalSnapPoint peer = (PositionalSnapPoint) peerPoint;
		return Calculus.length(absoluteA(), peer.absoluteA());
	}
	
	public SnapResultMove getSnapMove(ISnapPoint peerPoint) {
		if (!(peerPoint instanceof PositionalSnapPoint))
			return SnapResultMove.NONE;
		PositionalSnapPoint peer = (PositionalSnapPoint) peerPoint;
		MPoint delta = new MPoint(peer.absoluteA.x() - absoluteA.x(), peer.absoluteA.y() - absoluteA.y());
		return new SnapResultMove(true, delta, 0);
	}
	
	
	public String debugString(int tabs) {
		String str = "";
		for (int i = 0; i < tabs; i++) str += "\t";
		str += "OnePoints("+_parent.id()+"."+_id+"): nei("+(_peer != null ? _peer.id() : "*")+
			"), A: orig["+A.x()+","+A.y()+"],  rel["+relativeA.x()+","+relativeA.y()+"],  abs["+absoluteA.x()+","+absoluteA.y()+
			"])";
		return str;
	}
}
