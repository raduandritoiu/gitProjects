package radua.ui.models.snaps;

import java.awt.Point;
import java.util.ArrayList;

import radua.ui.models.SnapModel;
import radua.ui.utils.Calculus;
import radua.ui.utils.Rotations;


public class TwoSnapPoint extends BasicSnapPoint
{
	private static final int DELTA_SNAP = 30;
	
	final Point A, B;
	Point relativeA, relativeB;
	Point absoluteA, absoluteB;
	
	
	public TwoSnapPoint(SnapModel parent, Point pointA, Point pointB) { 
		this(parent, pointA.x, pointA.y, pointB.x, pointB.y);
	}
	public TwoSnapPoint(SnapModel parent, int xA, int yA, int xB, int yB) {
		super(parent);
		A = new Point(xA, yA);
		B = new Point(xB, yB);
		updatePoints();
	}
	
	
	public Point A() { return A; }
	public Point B() { return B; }
	public Point relativeA() { return relativeA; }
	public Point relativeB() { return relativeB; }
	public Point absoluteA() { return absoluteA; }
	public Point absoluteB() { return absoluteB; }
	
	
	void updatePoints() {
		relativeA = _parent.relativePoint(A);
		relativeB = _parent.relativePoint(B);
		absoluteA = _parent.absolutePoint(A);
		absoluteB = _parent.absolutePoint(B);
	}
	
	
	public ArrayList<DrawSnapPoint> getDrawPoints() {
		ArrayList<DrawSnapPoint> points = new ArrayList<>(2);
		points.add(new DrawSnapPoint(relativeA.x, relativeA.y, isSnapped()));
		points.add(new DrawSnapPoint(relativeB.x, relativeB.y, isSnapped()));
		return points;
	}
	
	
	public boolean canSnap(ISnapPoint snapPoint) {
		if (!(snapPoint instanceof TwoSnapPoint))
			return false;
		TwoSnapPoint two = (TwoSnapPoint) snapPoint;
		if (Math.abs(absoluteA.x - two.absoluteB.x) > DELTA_SNAP)
			return false;
		if (Math.abs(absoluteA.y - two.absoluteB.y) > DELTA_SNAP)
			return false;
		if (Math.abs(absoluteB.x - two.absoluteA.x) > DELTA_SNAP)
			return false;
		if (Math.abs(absoluteB.y - two.absoluteA.y) > DELTA_SNAP)
			return false;
		return true;
	}
	
	
	public SnapResultMove getSnapMove(ISnapPoint snapPoint) {
		if (!(snapPoint instanceof TwoSnapPoint))
			return new SnapResultMove(false, new Point(0, 0), 0);
		TwoSnapPoint two = (TwoSnapPoint) snapPoint;
		
		// first compute rotation
		double dr = 0;
		
		boolean p1 = Calculus.oyParallel(absoluteA, absoluteB);
		boolean p2 = Calculus.oyParallel(two.absoluteA, two.absoluteB);
		
		System.out.println("P1="+p1+"    P2="+p2);
		
		if (p1 && p2) {
			dr = 0;
		}
		else if (p1) {
			double m = Calculus.computeM(two.absoluteA, two.absoluteB);
			dr = Math.atan(m);
			System.out.println("P1-TRUE :  m="+m+"    dr="+dr+"    al="+(dr/Rotations.RAD_180)+"^");
		}
		else if (p2) {
			double m = Calculus.computeM(absoluteA, absoluteB);
			dr = Math.atan(m);
			System.out.println("P2-TRUE :  m="+m+"    dr="+dr+"    al="+(dr/Rotations.RAD_180*180)+"^");
		}
		else {
			double m1 = Calculus.computeM(absoluteA, absoluteB);
			double m2 = Calculus.computeM(two.absoluteA, two.absoluteB);
			double tan = (m2 - m1) / (1 - m1*m2);
			dr = Math.atan(tan);
			System.out.println("m1="+m1+"    m2="+m2+"    dr="+dr);
		}
		
		// 0^   - 0 		- 0
		// 30^  - 0.57 		- 0.5
		// 60^  - 1.73 		- 0.866
		// 90^  - inf 		- 1
		// 120^ - -1.73 	- 
		// 150^ - -0.57 	- 0.5
		// 180^ = 0 		- 0
		
		int dx1 = two.absoluteB.x - absoluteA.x;
		int dy1 = two.absoluteB.y - absoluteA.y;
		
//		
//		int dx2 = two.absoluteA.x - absoluteB.x;
//		int dy2 = two.absoluteA.y - absoluteB.y;
		
		return new SnapResultMove(true, new Point(0, 0), dr);
	}
	
	
	@Override
	public String debugString(int tabs) {
		String str = "";
		for (int i = 0; i < tabs; i++) str += "\t";
		str += "TwoPoints: A: orig["+A.x+","+A.y+"],  rel["+relativeA.x+","+relativeA.y+"],  abs["+absoluteA.x+","+absoluteA.y+"]\n";
		for (int i = 0; i < tabs; i++) str += "\t";
		str += "           B: orig["+B.x+","+B.y+"],  rel["+relativeB.x+","+relativeB.y+"],  abs["+absoluteB.x+","+absoluteB.y+"]";
		return str;
	}
}
