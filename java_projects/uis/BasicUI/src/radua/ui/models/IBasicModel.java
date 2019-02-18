package radua.ui.models;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import radua.ui.ids.ModelId;
import radua.ui.observers.IObserver;


public interface IBasicModel 
{
	ModelId id();
	
	void addObserver(IObserver observer);
	void removeObserver(IObserver observer);
	void removeObservers();

	Color getColor();
	void setColor(Color color);
	boolean isSelected();
	void setSelected(boolean selected);
	
	Point getPosition();
	int getX();
	int getY();
	Dimension getDimension();
	int getWidth();
	int getHeigth();
	double getRotation();
	
	void move(Point point);
	void move(int x, int y);
	void translate(Point point);
	void translate(int x, int y);
	void resize(Dimension dimension);
	void resize(int width, int height);
	void scale(float scale);
	void rotateDelta(double rotation);
	void rotate(double rotation);
	
	Point relativePoint(Point point);
	Point absolutePoint(Point point);
	Point relativeToAbsolute(Point point);
	
	String debugString(int tabs);
}
