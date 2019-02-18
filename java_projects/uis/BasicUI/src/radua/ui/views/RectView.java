package radua.ui.views;

import radua.ui.models.BasicModel;
import radua.ui.models.IBasicModel;
import radua.ui.views.painters.RectanglePainter;


public class RectView extends BasicView<IBasicModel>
{
	private static final long serialVersionUID = 2495903248217329441L;


	public RectView(BasicModel model) {
		super(model);
	}
	
	/** add the painter before the super ones */
	@Override
	protected void addInitialPainters() {
		_painters.add(RectanglePainter.Instance);
		super.addInitialPainters();
	}
}
