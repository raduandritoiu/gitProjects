package radua.ui.models;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import radua.ui.ids.IdManager;
import radua.ui.ids.ModelId;
import radua.ui.observers.IObservable;
import radua.ui.observers.IObserver;
import radua.ui.observers.ObservableEvent;
import radua.ui.utils.Calculus;


public class BasicModel implements IObservable, IBasicModel
{
	private final ModelId _id;
	private final Point _position;
	private final Dimension _dimension;
	private Color _color;
	private double _rotation;
	private boolean _selected;
	private final List<IObserver> _observers;
	
	
	public BasicModel() { this(100, 100, 100, 100, Color.GREEN); }
	public BasicModel(Point position, Dimension dimension) { this(position.x, position.y, dimension.width, dimension.height, Color.GREEN); }
	public BasicModel(Point position, Dimension dimension, Color color) { this(position.x, position.y, dimension.width, dimension.height, color); }
	public BasicModel(int x, int y, int width, int height) { this(x, y, width, height, Color.GREEN); } 
	public BasicModel(int x, int y, int width, int height, Color color) {
		_id = IdManager.GetModelId();
		_position = new Point(x, y);
		_dimension = new Dimension(width, height);
		_color = color;
		_rotation = 0;
		_selected = false;
		_observers = new ArrayList<>();
	}
	
	
	public ModelId id() {
		return _id;
	}
	
	
	public void addObserver(IObserver observer) { _observers.add(observer); }
	public void removeObserver(IObserver observer) { _observers.remove(observer); }
	public void removeObservers() { _observers.clear(); }
	protected void notifyObservers(ObservableEvent event, Object value) {
		for (IObserver observer : _observers) {
			observer.notify(this, event, value);
		}
	}

	
	public Color getColor() { return _color; }
	public void setColor(Color color) {
		Color tmp = _color;
		_color = color;
		notifyObservers(ObservableEvent.COLOR, tmp);
	}
	
	
	public boolean isSelected() { return _selected; }
	public void setSelected(boolean selected) {
		Boolean tmp = _selected;
		_selected = selected;
		notifyObservers(ObservableEvent.SELECT, tmp);
	}
	
	
	public Point getPosition() { return new Point(_position); }
	public int getX() { return _position.x; }
	public int getY() { return _position.y; }
	
	public Dimension getDimension() { return new Dimension(_dimension); }
	public int getWidth() { return _dimension.height; }
	public int getHeigth() { return _dimension.width; }
	
	public double getRotation() { return _rotation; }
	
	
	public void move(Point point) { move(point.x, point.y); }
	public void move(int x, int y) {
		Point tmp = new Point(_position.x, _position.y);
		_position.move(x, y);
		moveLogic(tmp);
		notifyObservers(ObservableEvent.MOVE, tmp);
	}
	
	public void translate(Point point) { translate(point.x, point.y); }
	public void translate(int x, int y) {
		Point tmp = new Point(_position.x, _position.y);
		_position.move(_position.x + x, _position.y + y);
		moveLogic(tmp);
		notifyObservers(ObservableEvent.MOVE, tmp);
	}
	
	public void resize(Dimension dimension) { resize(dimension.width, dimension.height); }
	public void resize(int width, int height) {
		Dimension tmp = new Dimension(_dimension.width, _dimension.height);
		_dimension.setSize(width, height);
		resizeLogic(tmp);
		notifyObservers(ObservableEvent.RESIZE, tmp);
	}
	
	public void scale(float scale) {
		Dimension tmp = new Dimension(_dimension.width, _dimension.height);
		_dimension.setSize(_dimension.width * scale, _dimension.height * scale);
		resizeLogic(tmp);
		notifyObservers(ObservableEvent.RESIZE, tmp);
	}
	
	public void rotateDelta(double rotation) { rotate(_rotation + rotation); }
	public void rotate(double rotation) {
		double tmp = _rotation;
		_rotation = rotation;
		rotateLogic(tmp);
		notifyObservers(ObservableEvent.ROTATE, new Double(tmp));
	}
	
	
	protected void moveLogic(Point oldPosition) {}
	protected void resizeLogic(Dimension oldDimension) {}
	protected void rotateLogic(double oldRotation) {}
	
	
	public Point relativePoint(Point point) {
		return Calculus.rotatePoint(point, _rotation, _dimension.width/2, _dimension.height/2);
	}
	
	public Point absolutePoint(Point point) {
		Point newPoint = relativePoint(point);
		newPoint.x += _position.x;
		newPoint.y += _position.y;
		return newPoint;
	}
	
	public Point relativeToAbsolute(Point point) {
		return new Point(point.x + _position.x, point.y + _position.y);
	}
	
	
	@Override
	public String toString() {
		return debugString(0);
	}
	public String debugString(int tabs) {
		String str = "";
		for (int i = 0; i < tabs; i++) str += "\t";
		str += this.getClass().getSimpleName()+" ("+_id+"): pos["+_position.x+","+_position.y+
			"],  dim["+_dimension.width+","+_dimension.height+"],  rot("+Calculus.RadToDeg(_rotation)+"^),  color("+_color+
			"),  isSelected("+_selected+") ";
		return str;
	}
}
