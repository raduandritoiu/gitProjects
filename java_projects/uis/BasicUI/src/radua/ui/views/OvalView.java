package radua.ui.views;

import radua.ui.models.BasicModel;
import radua.ui.views.painters.OvalPainter;


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
