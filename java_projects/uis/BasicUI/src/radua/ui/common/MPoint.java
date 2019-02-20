package radua.ui.common;

public class MPoint implements IWritablePoint
{
	private double _x;
	private double _y;
	
	public MPoint() { this((double) 0, (double) 0); }
	public MPoint(double x, double y) {
		_x = x; _y = y;
	}
	
	public void x(double x) { _x = x; }
	public void y(double y) { _y = y; }
	public double x() { return _x; }
	public double y() { return _y; }
	
	public int intX() { return (int) _x; }
	public int intY() { return (int) _y; }
	
	public MPoint moveTo(IReadablePoint point) { _x = point.x(); _y = point.y(); return this; }
	public MPoint moveTo(double x, double y) { _x = x; _y = y; return this; }
	
	public MPoint moveBy(IReadablePoint point) { _x += point.x(); _y += point.y(); return this; }
	public MPoint moveBy(double x, double y)  { _x += x; _y += y; return this; }

	public MPoint scale(double ds)  { _x *= ds; _y *= ds; return this; }
	
	
	public IReadablePoint clone() { return new MPoint(_x, _y); }

	public String toString() {
		return "MPoint["+_x+","+_y+"]";
	}
}
