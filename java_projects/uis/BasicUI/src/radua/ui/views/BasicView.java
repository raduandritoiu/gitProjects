package radua.ui.views;

import java.awt.Graphics;
import java.util.ArrayList;

import radua.ui.display.insulator.MyUIComponent;
import radua.ui.display.painters.IPainter;
import radua.ui.logic.ids.IdManager;
import radua.ui.logic.ids.ViewId;
import radua.ui.logic.models.IBasicModel;
import radua.ui.logic.observers.IPropertyObservable;
import radua.ui.logic.observers.ObservableProperty;


public abstract class BasicView<MDL extends IBasicModel> extends MyUIComponent implements IBaseView<MDL>
{
	private static final long serialVersionUID = -2171496473623776146L;
	
	private final ViewId _id;
	private final MDL _model;
    protected final ArrayList<IPainter> _painters;
	
    
	public BasicView(MDL model) {
		_id = IdManager.GetViewId();
		_painters = new ArrayList<>();
		_model = model;
		_model.addObserver(this);
        setLocation((int) _model.x(), (int) _model.y());
        setSize((int) _model.width(), (int) _model.height());
        setOpaque(false);
        addInitialPainters();
	}
	
	
	public final ViewId id() { return _id; }
	
	public final MDL model() { return _model; }
	
	
	protected void addInitialPainters() {}
	public void addPainter(IPainter painter) {
		_painters.add(painter);
	}
	protected void applyPainters(Graphics g) {
		for (IPainter painter : _painters) {
			painter.paint(this, g);
		}
	}
	
	
	@SuppressWarnings("incomplete-switch")
	public void notify(IPropertyObservable observable, ObservableProperty event, Object oldValue) {
		switch (event) {
			case MOVE:
		        setLocation((int) _model.x(), (int) _model.y());
		        return;
			case RESIZE:
				setSize((int) _model.width(), (int) _model.height());
		        return;
			case ROTATE:
				doRotate(oldValue);
		        return;
			case COLOR:
				doRedrawBackground(oldValue);
				return;
			case VISIBLE:
				doRedrawBackground(oldValue);
				return;
			case SELECT:
				setFocusable(_model.isSelected());
				doRedrawBackground(oldValue);
				return;
			case ALL:
				doRedrawAll(oldValue);
		        return;
		}
	}
	
	
	protected void doRotate(Object oldValue) {}
	protected void doRedrawBackground(Object oldValue) {
		repaint();
	}
	protected void doRedrawAll(Object oldValue) {
		repaint();
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		applyPainters(g);
	}
}