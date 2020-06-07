package radua.ui.logic.models.snaps;

import radua.ui.logic.basics.IReadablePoint;
import radua.ui.logic.basics.MPoint;
import radua.ui.logic.models.SnapModel;
import radua.ui.logic.utils.Calculus;
import radua.ui.logic.utils.Constants;

public class DirectionalSnapPoint extends PositionalSnapPoint
{
	final double rotation;
	
	
	public DirectionalSnapPoint(SnapModel parent, IReadablePoint pointA, double rot) { 
		this(parent, pointA.x(), pointA.y(), rot); 
	}
	public DirectionalSnapPoint(SnapModel parent, double x, double y, double rot) {
		super(parent, x, y);
		rotation = rot;
		updatePoints();
	}
	
	
	protected boolean acceptsTypeForSnap(ISnapPoint peerPoint) {
		return (peerPoint instanceof DirectionalSnapPoint);
	}
	
	
	public SnapResultMove getSnapMove(ISnapPoint peerPoint) {
		if (!(peerPoint instanceof DirectionalSnapPoint))
			return SnapResultMove.NONE;
		DirectionalSnapPoint one = (DirectionalSnapPoint) peerPoint;
		
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
	
	
	public String debugString(int tabs) {
		String str = "";
		for (int i = 0; i < tabs; i++) str += "\t";
		str += "OnePoints("+_parent.id()+"."+_id+"): nei("+(_peer != null ? _peer.id() : "*")+
			"), A: orig["+A.x()+","+A.y()+"],  rel["+relativeA.x()+","+relativeA.y()+"],  abs["+absoluteA.x()+","+absoluteA.y()+
			"],  rot("+Calculus.RadToDeg(rotation)+"^),  absRot("+Calculus.RadToDeg(rotation + _parent.rotation())+"^)";
		return str;
	}
}
