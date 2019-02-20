package radua.ui.models;

import java.awt.Color;

import radua.ui.common.IReadablePoint;
import radua.ui.common.IReadableSize;
import radua.ui.common.IWritablePoint;
import radua.ui.ids.ModelId;
import radua.ui.observers.IObserver;
import radua.ui.observers.ObservableEvent;


public interface IBasicModel 
{
	ModelId id();
	
	void addObserver(IObserver observer);
	void removeObserver(IObserver observer);
	void removeObservers();

	Color getColor();
	void setColor(Color color);
	boolean isSelected();
	void select(boolean selected);
	
	IReadablePoint position();
	double x();
	double y();
	IReadableSize size();
	double width();
	double height();
	double rotation();
	
	void moveTo(IReadablePoint point);
	void moveTo(double x, double y);
	void moveBy(IReadablePoint point);
	void moveBy(double x, double y);
	void resizeTo(IReadableSize size);
	void resizeTo(double width, double height);
	void scale(float scale);
	void rotateBy(double rotation);
	void rotateTo(double rotation);
	
	void relativePoint(IReadablePoint point, IWritablePoint result);
	void absolutePoint(IReadablePoint point, IWritablePoint result);
	void relativeToAbsolute(IReadablePoint point, IWritablePoint result);
	
	void notifyObservers(ObservableEvent event, Object value);
	
	
	String debugString(int tabs);
}
