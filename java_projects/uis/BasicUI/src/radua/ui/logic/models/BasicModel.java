package radua.ui.logic.models;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import radua.ui.logic.basics.IReadablePoint;
import radua.ui.logic.basics.IReadableSize;
import radua.ui.logic.basics.IWritablePoint;
import radua.ui.logic.basics.MPoint;
import radua.ui.logic.basics.MSize;
import radua.ui.logic.ids.IdManager;
import radua.ui.logic.ids.ModelId;
import radua.ui.logic.observers.IObservable;
import radua.ui.logic.observers.IObserver;
import radua.ui.logic.observers.ObservableEvent;
import radua.ui.logic.utils.Calculus;


public class BasicModel implements IObservable, IBasicModel
{
	private final ModelId _id;
	private final IWritablePoint _position;
	private final MSize _dimension;
	protected Color _color;
	private double _rotation;
	private boolean _selected;
	private final List<IObserver> _observers;
	
	
	public BasicModel(IReadablePoint position, IReadableSize size, Color color) {
		this(position.x(), position.y(), size.width(), size.height(), color);
	}
	public BasicModel(double x, double y, double width, double height, Color color) {
		_id = IdManager.GetModelId();
		_position = new MPoint(x, y);
		_dimension = new MSize(width, height);
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
	public void notifyObservers(ObservableEvent event, Object value) {
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
	public void select(boolean selected) {
		Boolean tmp = _selected;
		_selected = selected;
		notifyObservers(ObservableEvent.SELECT, tmp);
	}
	
	
	public IReadablePoint position() { return _position; }
	public double x() { return _position.x(); }
	public double y() { return _position.y(); }
	
	public IReadableSize size() { return _dimension; }
	public double width() { return _dimension.width(); }
	public double height() { return _dimension.height(); }
	
	public double rotation() { return _rotation; }
	
	
	public void moveTo(IReadablePoint point) { moveTo(point.x(), point.y()); }
	public void moveTo(double x, double y) {
		if (_position.x() == x && _position.y() == y)
			return;
		IReadablePoint tmp = _position.clone();
		_position.moveTo(x, y);
		moveLogic(tmp);
		notifyObservers(ObservableEvent.MOVE, tmp);
	}
	
	public void moveBy(IReadablePoint point) { moveBy(point.x(), point.y()); }
	public void moveBy(double x, double y) {
		if (x == 0 && y == 0) 
			return;
		IReadablePoint tmp = _position.clone();
		_position.moveBy(x, y);
		moveLogic(tmp);
		notifyObservers(ObservableEvent.MOVE, tmp);
	}
	
	public void resizeTo(IReadableSize dimension) { resizeTo(dimension.width(), dimension.height()); }
	public void resizeTo(double width, double height) {
		if (_dimension.width() == width && _dimension.height() == height)
			return;
		IReadableSize tmp = _dimension.clone();
		_dimension.resizeTo(width, height);
		resizeLogic(tmp);
		notifyObservers(ObservableEvent.RESIZE, tmp);
	}
	
	public void scale(float scale) {
		if (scale == 1)
			return;
		IReadableSize tmp = _dimension.clone();
		_dimension.scale(scale);
		resizeLogic(tmp);
		notifyObservers(ObservableEvent.RESIZE, tmp);
	}
	
	public void rotateBy(double deltaRotation) { 
		if (deltaRotation == 0)
			return;
		rotateTo(_rotation + deltaRotation); 
	}
	public void rotateTo(double rotation) {
		if (_rotation == rotation)
			return;
		double tmp = _rotation;
		_rotation = rotation;
		rotateLogic(tmp);
		notifyObservers(ObservableEvent.ROTATE, new Double(tmp));
	}
	
	
	protected void moveLogic(IReadablePoint oldPosition) {}
	protected void resizeLogic(IReadableSize oldSize) {}
	protected void rotateLogic(double oldRotation) {}
	
	
	public void relativePoint(IReadablePoint point, IWritablePoint result) {
		Calculus.rotatePoint(point, _rotation, _dimension.width()/2, _dimension.height()/2, result);
	}
	
	public void absolutePoint(IReadablePoint point, IWritablePoint result) {
		relativePoint(point, result);
		result.moveBy(_position);
	}
	
	public void relativeToAbsolute(IReadablePoint point, IWritablePoint result) {
		result.moveTo(point);
		result.moveBy(_position);
	}
	
	
	@Override
	public String toString() {
		return debugString(0);
	}
	public String debugString(int tabs) {
		String str = "";
		for (int i = 0; i < tabs; i++) str += "\t";
		str += this.getClass().getSimpleName()+"("+_id+"): pos["+_position.x()+","+_position.y()+
			"],  dim["+_dimension.width()+","+_dimension.height()+"],  rot("+Calculus.RadToDeg(_rotation)+
			"^),  isSelected("+_selected+") ";
		return str;
	}
}
