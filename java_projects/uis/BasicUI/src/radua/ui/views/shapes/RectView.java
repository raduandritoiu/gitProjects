package radua.ui.views.shapes;

import radua.ui.display.painters.RectanglePainter;
import radua.ui.logic.models.IBasicModel;
import radua.ui.views.BasicView;


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
