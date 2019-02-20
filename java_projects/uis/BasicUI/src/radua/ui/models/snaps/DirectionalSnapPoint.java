package radua.ui.models.snaps;

import java.util.ArrayList;

import radua.ui.common.IReadablePoint;
import radua.ui.common.IWritablePoint;
import radua.ui.common.MPoint;
import radua.ui.models.SnapModel;
import radua.ui.utils.Calculus;
import radua.ui.utils.Constants;

public class DirectionalSnapPoint extends BasicSnapPoint
{
	private static final double DELTA_SNAP = 30;
	
	final IWritablePoint A;
	final double rotation;
	IWritablePoint relativeA, absoluteA;
	
	
	public DirectionalSnapPoint(SnapModel parent, IReadablePoint pointA, double rot) { 
		this(parent, pointA.x(), pointA.y(), rot); 
	}
	public DirectionalSnapPoint(SnapModel parent, double x, double y, double rot) {
		super(parent);
		A = new MPoint(x, y);
		relativeA = new MPoint(x, y);
		absoluteA = new MPoint(x, y);
		rotation = rot;
		updatePoints();
	}
	
	
	public IReadablePoint A() { return A; }
	public IReadablePoint relativeA() { return relativeA; }
	public IReadablePoint absoluteA() { return absoluteA; }

	
	@Override
	void updatePoints() {
		_parent.relativePoint(A, relativeA);
		_parent.absolutePoint(A, absoluteA );
	}
	
	public ArrayList<DrawSnapPoint> getDrawPoints() {
		ArrayList<DrawSnapPoint> points = new ArrayList<>(2);
		points.add(new DrawSnapPoint(relativeA.intX(), relativeA.intY(), isSnapped()));
		return points;
	}

	public boolean canSnap(ISnapPoint snapPoint) {
		if (snapPoint.getSnap() != null && snapPoint.getSnap() != this) {
			return false;
		}
		if (!(snapPoint instanceof DirectionalSnapPoint))
			return false;
		DirectionalSnapPoint one = (DirectionalSnapPoint) snapPoint;
		if (Math.abs(absoluteA.x() - one.absoluteA.x()) > DELTA_SNAP)
			return false;
		if (Math.abs(absoluteA.y() - one.absoluteA.y()) > DELTA_SNAP)
			return false;
		return true;
	}
	
	public double getSnapStength(ISnapPoint snapPoint) {
		if (!(snapPoint instanceof DirectionalSnapPoint))
			return Constants.MAX_INF;
		DirectionalSnapPoint one = (DirectionalSnapPoint) snapPoint;
		return Calculus.length(absoluteA(), one.absoluteA());
	}
	
	public SnapResultMove getSnapMove(ISnapPoint snapPoint) {
		if (!(snapPoint instanceof DirectionalSnapPoint))
			return new SnapResultMove(false, new MPoint(0, 0), 0);
		DirectionalSnapPoint one = (DirectionalSnapPoint) snapPoint;
		
		// compute rotation
		double destRotation = Calculus.NormalRad(one.rotation + one._parent.rotation() + Constants.RAD_180 - rotation);
		double deltaRotation = Calculus.NormalRad(destRotation - _parent.rotation());

		// compute translation - taking into account the rotation
		MPoint destPoint = new MPoint();
		Calculus.rotatePoint(A, destRotation, _parent.width()/2, _parent.height()/2, destPoint);
		_parent.relativeToAbsolute(destPoint, destPoint);
		
		MPoint delta = new MPoint(one.absoluteA.x() - destPoint.x(), one.absoluteA.y() - destPoint.y());
		return new SnapResultMove(true, delta, deltaRotation);
	}
	
	
	@Override
	public String debugString(int tabs) {
		String str = "";
		for (int i = 0; i < tabs; i++) str += "\t";
		str += "OnePoints("+_parent.id()+"."+_id+"): nei("+(_neighbour != null ? _neighbour.id() : "*")+
			"), A: orig["+A.x()+","+A.y()+"],  rel["+relativeA.x()+","+relativeA.y()+"],  abs["+absoluteA.x()+","+absoluteA.y()+
			"],  rot("+Calculus.RadToDeg(rotation)+"^),  absRot("+Calculus.RadToDeg(rotation + _parent.rotation())+"^)";
		return str;
	}
}
