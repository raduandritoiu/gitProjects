package radua.ui.views.shapes;

import radua.ui.display.painters.OvalPainter;
import radua.ui.logic.models.IBasicModel;
import radua.ui.views.BasicView;


public class OvalView extends BasicView<IBasicModel>
{
	private static final long serialVersionUID = 1245096007839700150L;


	public OvalView(IBasicModel model) {
		super(model);
	}
	
	/** add the painter before the super ones */
	@Override
	protected void addInitialPainters() {
		_painters.add(OvalPainter.Instance);
		super.addInitialPainters();
	}
}
