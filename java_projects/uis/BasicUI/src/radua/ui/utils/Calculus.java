package radua.ui.utils;

import radua.ui.common.IReadablePoint;
import radua.ui.common.IWritablePoint;


public class Calculus 
{
	
	
	public static int RadToDeg(double rad) {
		return (int) Math.round(rad / Constants.RAD_180 * 180);
	}
	
	public static double DegToRad(int deg) {
		return NormalRad((double)deg / 180 * Constants.RAD_180);
	}
	
	
	public static int NormalDeg(int deg) {
		deg %= Constants.DEG_360;
		if (deg < 0) {
			deg += Constants.DEG_360;
		}
		return deg;
	}
	
	public static double NormalRad(double rad) {
		double fu = rad / Constants.RAD_360;
		fu = Math.floor(fu);
		fu = fu * Constants.RAD_360;
		return rad - Math.floor(rad / Constants.RAD_360) * Constants.RAD_360;
	}
	
	
	public static boolean oyParallel(IReadablePoint A, IReadablePoint B) {
		return A.x() == B.x();
	}	
	
	
	public static double length(IReadablePoint A, IReadablePoint B) {
		return Math.sqrt((A.x() - B.x())*(A.x() - B.x()) + (A.y() - B.y())*(A.y() - B.y()));
	}
	
	
	public static double computeM(IReadablePoint A, IReadablePoint B) {
		double m = ((double) (B.y() - A.y())) / ((double) (B.x() - A.x()));
		return m;
	}
	
	
	public static double diffRotate(double curRotate, double destRotate) {
		return 0;
	}
	
	
	public static void rotatePoint(IReadablePoint point, double rotation, double centerX, double centerY, IWritablePoint result) {
		double cos = Math.cos(rotation);
		double sin = Math.sin(rotation);
		double x = point.x() - centerX;
		double y = point.y() - centerY;
		double rx = x*cos - y*sin;
		double ry = x*sin + y*cos;
		rx += centerX;
		ry += centerY;
		result.moveTo(rx, ry);
	}

}
