package radua.ui.utils;

import java.awt.Point;


public class Calculus 
{
	public static int RadToDeg(double rad) {
//		return NormalDeg((int) Math.round(rad / Math.PI * 180));
		return (int) Math.round(rad / Rotations.RAD_180 * 180);
	}
	
	public static double DegToRad(int deg) {
		return NormalRad((double)deg / 180 * Rotations.RAD_180);
	}
	
	
	public static int NormalDeg(int deg) {
		deg %= Rotations.DEG_360;
		if (deg < 0) {
			deg += Rotations.DEG_360;
		}
		return deg;
	}
	
	public static double NormalRad(double rad) {
		return rad - Math.floor(rad / Rotations.RAD_360) * Rotations.RAD_360;
	}
	
	
	public static boolean oyParallel(Point A, Point B) {
		return A.x == B.x;
	}	
	
	
	public static double computeM(Point A, Point B) {
		double m = ((double) (B.y - A.y)) / ((double) (B.x - A.x));
		return m;
	}
	
	
	public static double diffRotate(double curRotate, double destRotate) {
		return 0;
	}
	
	
	public static Point rotatePoint(Point point, double rotation, int centerX, int centerY) {
		double cos = Math.cos(rotation);
		double sin = Math.sin(rotation);
		int x = point.x - centerX;
		int y = point.y - centerY;
		int rx = (int) Math.round(x*cos - y*sin);
		int ry = (int) Math.round(x*sin + y*cos);
		rx += centerX;
		ry += centerY;
		return new Point(rx, ry);
	}

}
