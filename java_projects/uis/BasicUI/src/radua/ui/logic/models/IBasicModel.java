package radua.ui.logic.models;

import java.awt.Color;

import radua.ui.logic.basics.IReadablePoint;
import radua.ui.logic.basics.IReadableSize;
import radua.ui.logic.basics.IWritablePoint;
import radua.ui.logic.ids.ModelId;
import radua.ui.logic.observers.IPropertyObserver;
import radua.ui.logic.observers.ObservableProperty;


public interface IBasicModel 
{
	ModelId id();
	
	void addObserver(IPropertyObserver observer);
	void removeObserver(IPropertyObserver observer);
	void removeObservers();

	Color getColor();
	void setColor(Color color);
	boolean isVisible();
	void visible(boolean visible);
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
	
	void notifyObservers(ObservableProperty event, Object value);
	
	
	String debugString(int tabs);
}
