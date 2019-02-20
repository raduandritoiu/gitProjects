package radua.ui.views;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JComponent;

import radua.ui.models.IBasicModel;
import radua.ui.observers.IObservable;
import radua.ui.observers.IObserver;
import radua.ui.observers.ObservableEvent;
import radua.ui.views.painters.IPainter;


public abstract class BasicView<MDL extends IBasicModel> extends JComponent implements IObserver
{
	private static final long serialVersionUID = -2171496473623776146L;
	
	
	private final MDL _model;
    protected final ArrayList<IPainter> _painters;
	
    
	public BasicView(MDL model) {
		_painters = new ArrayList<>();
		_model = model;
		_model.addObserver(this);
        setLocation((int) _model.x(), (int) _model.y());
        setSize((int) _model.width(), (int) _model.height());
        setOpaque(false);
        addInitialPainters();
	}
	
	
	public final MDL getModel() { return _model; }
	
	
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
	public void notify(IObservable observable, ObservableEvent event, Object value) {
		switch (event) {
			case MOVE:
		        setLocation((int) _model.x(), (int) _model.y());
		        return;
			case RESIZE:
				setSize((int) _model.width(), (int) _model.height());
		        return;
			case ROTATE:
				doRotate(value);
		        return;
			case COLOR:
				doRedrawBack(value);
				return;
			case SELECT:
				doRedrawBack(value);
				return;
			case ALL:
				doRedrawAll(value);
		        return;
		}
	}
	
	
	protected void doRotate(Object value) {}
	protected void doRedrawBack(Object value) {
		repaint();
	}
	protected void doRedrawAll(Object value) {
		repaint();
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		applyPainters(g);
	}
	
	
	/** keep this method available */
	protected void superPaint(Graphics g) { super.paintComponent(g); }
}
