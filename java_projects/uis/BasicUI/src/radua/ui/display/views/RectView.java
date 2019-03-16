package radua.ui.display.views;

import radua.ui.display.views.painters.RectanglePainter;
import radua.ui.logic.models.IBasicModel;


public class RectView extends BasicView<IBasicModel>
{
	private static final long serialVersionUID = 2495903248217329441L;


	public RectView(IBasicModel model) {
		super(model);
	}
	
	/** add the painter before the super ones */
	@Override
	protected void addInitialPainters() {
		_painters.add(RectanglePainter.Instance);
		super.addInitialPainters();
	}
}
