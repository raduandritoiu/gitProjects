package radua.ui.logic.basics;

public interface IReadablePoint {
	double x();
	double y();
	
	int intX();
	int intY();
	
	IReadablePoint clone();
}
