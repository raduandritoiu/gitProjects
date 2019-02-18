package radua.ui.views;

import radua.ui.models.ISnapModel;
import radua.ui.observers.IObservable;
import radua.ui.observers.ObservableEvent;
import radua.ui.views.painters.SnapPointsPainter;


public abstract class SnapView<SNPMDL extends ISnapModel> extends BasicView<SNPMDL>
{
	private static final long serialVersionUID = -5458541248680729980L;
	
	
	public SnapView(SNPMDL model) {
		super(model);
	}
	
	/** add the painter after the super ones */
	@Override
	protected void addInitialPainters() {
		super.addInitialPainters();
		_painters.add(SnapPointsPainter.Instance);
	}
	
	
	@Override
	@SuppressWarnings("incomplete-switch")
	public void notify(IObservable observable, ObservableEvent event, Object value) {
		switch (event) {
			case SNAP_CHANGE:
				repaint();
				break;
		}
		super.notify(observable, event, value);
	}
	
	
	@Override
	protected void doRotate(Object value) {
		repaint();
	}
}
