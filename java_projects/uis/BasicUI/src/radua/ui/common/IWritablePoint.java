package radua.ui.common;

public interface IWritablePoint extends IReadablePoint
{
	void x(double x);
	void y(double y);
	
	IWritablePoint moveTo(IReadablePoint point);
	IWritablePoint moveTo(double x, double y);
	
	IWritablePoint moveBy(IReadablePoint point);
	IWritablePoint moveBy(double x, double y);
	
	IWritablePoint scale(double ds);
}
