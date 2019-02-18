package radua.ui.models.snaps;

import java.awt.Point;
import java.util.ArrayList;

import radua.ui.models.SnapModel;
import radua.ui.utils.Calculus;
import radua.ui.utils.Rotations;

public class DirectionalSnapPoint extends BasicSnapPoint
{
	private static final int DELTA_SNAP = 30;
	
	final Point A;
	final double rotation;
	Point relativeA, absoluteA;
	
	
	public DirectionalSnapPoint(SnapModel parent, Point pointA) { 
		this(parent, pointA.x, pointA.y, 0); 
	}
	public DirectionalSnapPoint(SnapModel parent, Point pointA, double rot) { 
		this(parent, pointA.x, pointA.y, rot); 
	}
	public DirectionalSnapPoint(SnapModel parent, int x, int y) {
		this(parent, x, y, 0); 
	}
	public DirectionalSnapPoint(SnapModel parent, int x, int y, double rot) {
		super(parent);
		A = new Point(x, y);
		rotation = rot;
		updatePoints();
	}
	
	
	public Point A() { return A; }
	public Point relativeA() { return relativeA; }
	public Point absoluteA() { return absoluteA; }

	
	@Override
	void updatePoints() {
		relativeA = _parent.relativePoint(A);
		absoluteA = _parent.absolutePoint(A);
	}
	
	public ArrayList<DrawSnapPoint> getDrawPoints() {
		ArrayList<DrawSnapPoint> points = new ArrayList<>(2);
		points.add(new DrawSnapPoint(relativeA.x, relativeA.y, isSnapped()));
		return points;
	}

	public boolean canSnap(ISnapPoint snapPoint) {
		if (_neighbour != null){
			if (_neighbour == snapPoint)
				return true;
			else
				return false;
		}
		if (!(snapPoint instanceof DirectionalSnapPoint))
			return false;
		DirectionalSnapPoint one = (DirectionalSnapPoint) snapPoint;
		if (Math.abs(absoluteA.x - one.absoluteA.x) > DELTA_SNAP)
			return false;
		if (Math.abs(absoluteA.y - one.absoluteA.y) > DELTA_SNAP)
			return false;
		return true;
	}
	
	public SnapResultMove getSnapMove(ISnapPoint snapPoint) { 
		if (!(snapPoint instanceof DirectionalSnapPoint))
			return new SnapResultMove(false, new Point(0, 0), 0);
		DirectionalSnapPoint one = (DirectionalSnapPoint) snapPoint;
		
		// compute rotation
		double destRotation = (one.rotation + one._parent.getRotation()) + Rotations.RAD_180 - rotation;
		double deltaRotation = destRotation - _parent.getRotation();

		// compute translation - taking into account the rotation
		Point destPoint = Calculus.rotatePoint(A, destRotation, _parent.getWidth()/2, _parent.getHeigth()/2);
		destPoint = _parent.relativeToAbsolute(destPoint);
		int deltaX = one.absoluteA.x - destPoint.x;
		int deltaY = one.absoluteA.y - destPoint.y;
		
		return new SnapResultMove(true, new Point(deltaX, deltaY), deltaRotation);
	}
	
	
	@Override
	public String debugString(int tabs) {
		String str = "";
		for (int i = 0; i < tabs; i++) str += "\t";
		str += "OnePoints: A: orig["+A.x+","+A.y+"],  rel["+relativeA.x+","+relativeA.y+"],  abs["+absoluteA.x+","+absoluteA.y+
			"],  rot("+Calculus.RadToDeg(rotation)+"^),  absRot("+Calculus.RadToDeg(rotation + _parent.getRotation())+"^),  neightbour("+(_neighbour != null)+")";
		return str;
	}

}
