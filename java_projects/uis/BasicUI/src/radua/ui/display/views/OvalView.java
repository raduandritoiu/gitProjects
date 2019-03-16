package radua.ui.display.views;

import radua.ui.display.views.painters.OvalPainter;
import radua.ui.logic.models.BasicModel;


public class OvalView extends BasicView<BasicModel>
{
	private static final long serialVersionUID = 1245096007839700150L;


	public OvalView(BasicModel model) {
		super(model);
	}
	
	/** add the painter before the super ones */
	@Override
	protected void addInitialPainters() {
		_painters.add(OvalPainter.Instance);
		super.addInitialPainters();
	}
}
