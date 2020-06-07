package radua.ui.logic.models;

import java.util.ArrayList;
import java.util.List;

import radua.ui.logic.basics.IReadablePoint;
import radua.ui.logic.basics.IReadableSize;
import radua.ui.logic.basics.IWritablePoint;
import radua.ui.logic.basics.MColor;
import radua.ui.logic.basics.MPoint;
import radua.ui.logic.basics.MSize;
import radua.ui.logic.ids.IdManager;
import radua.ui.logic.ids.ModelId;
import radua.ui.logic.observers.IPropertyObservable;
import radua.ui.logic.observers.IPropertyObserver;
import radua.ui.logic.observers.ObservableProperty;
import radua.ui.logic.utils.Calculus;


public abstract class BasicModel implements IPropertyObservable, IBasicModel
{
	private final ModelId _id;
	private final IWritablePoint _position;
	private final MSize _dimension;
	protected MColor _color;
	private double _rotation;
	private boolean _visible;
	private boolean _selected;
	private final List<IPropertyObserver> _observers;
	
	
	public BasicModel(IReadablePoint position, IReadableSize size, boolean visible, MColor color) {
		this(position.x(), position.y(), size.width(), size.height(), visible, color);
	}
	public BasicModel(double x, double y, double width, double height, boolean visible, MColor color) {
		_id = IdManager.GetModelId();
		_position = new MPoint(x, y);
		_dimension = new MSize(width, height);
		_color = color;
		_rotation = 0;
		_visible = true;
		_selected = false;
		_observers = new ArrayList<>();
	}
	
	
	public ModelId id() { return _id; }
	public String type() { return getClass().getSimpleName(); }
	
	
	public void addObserver(IPropertyObserver observer) { _observers.add(observer); }
	public void removeObserver(IPropertyObserver observer) { _observers.remove(observer); }
	public void removeObservers() { _observers.clear(); }
	public void notifyObservers(ObservableProperty event, Object value) {
		for (IPropertyObserver observer : _observers) {
			observer.notify(this, event, value);
		}
	}

	public MColor getColor() { return _color; }
	public void setColor(MColor color) {
		MColor tmp = _color;
		_color = color;
		notifyObservers(ObservableProperty.COLOR, tmp);
	}
	
	public boolean isVisible() { return _visible; }
	public void visible(boolean visible) {
		Boolean tmp = _visible;
		_visible = visible;
		notifyObservers(ObservableProperty.VISIBLE, tmp);
	}
	
	public boolean isSelected() { return _selected; }
	public void select(boolean selected) {
		Boolean tmp = _selected;
		_selected = selected;
		notifyObservers(ObservableProperty.SELECT, tmp);
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
		notifyObservers(ObservableProperty.MOVE, tmp);
	}
	
	public void moveBy(IReadablePoint point) { moveBy(point.x(), point.y()); }
	public void moveBy(double x, double y) {
		if (x == 0 && y == 0) 
			return;
		IReadablePoint tmp = _position.clone();
		_position.moveBy(x, y);
		moveLogic(tmp);
		notifyObservers(ObservableProperty.MOVE, tmp);
	}
	
	public void resizeTo(IReadableSize dimension) { resizeTo(dimension.width(), dimension.height()); }
	public void resizeTo(double width, double height) {
		if (_dimension.width() == width && _dimension.height() == height)
			return;
		IReadableSize tmp = _dimension.clone();
		_dimension.resizeTo(width, height);
		resizeLogic(tmp);
		notifyObservers(ObservableProperty.RESIZE, tmp);
	}
	
	public void scale(float scale) {
		if (scale == 1)
			return;
		IReadableSize tmp = _dimension.clone();
		_dimension.scale(scale);
		resizeLogic(tmp);
		notifyObservers(ObservableProperty.RESIZE, tmp);
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
		notifyObservers(ObservableProperty.ROTATE, new Double(tmp));
	}
	
	
	protected void moveLogic(IReadablePoint oldPosition) {}
	protected void resizeLogic(IReadableSize oldSize) {}
	protected void rotateLogic(double oldRotation) {}
	
	
	public void relativePoint(IReadablePoint point, IWritablePoint result) {
		Calculus.rotatePoint(point, _rotation, _dimension.width()/2, _dimension.height()/2, result);
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
