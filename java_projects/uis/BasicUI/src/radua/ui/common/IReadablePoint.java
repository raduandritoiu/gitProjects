package radua.ui.common;

public interface IReadablePoint {
	double x();
	double y();
	
	int intX();
	int intY();
	
	IReadablePoint clone();
}
