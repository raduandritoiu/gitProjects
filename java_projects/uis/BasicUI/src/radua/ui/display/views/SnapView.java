package radua.ui.display.views;

import radua.ui.display.views.painters.SnapPointsPainter;
import radua.ui.logic.models.ISnapModel;
import radua.ui.logic.observers.IPropertyObservable;
import radua.ui.logic.observers.ObservableProperty;


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
	public void notify(IPropertyObservable observable, ObservableProperty event, Object value) {
		switch (event) {
			case SNAP:
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
