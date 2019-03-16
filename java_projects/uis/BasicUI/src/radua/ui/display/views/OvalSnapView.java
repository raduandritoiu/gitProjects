package radua.ui.display.views;

import radua.ui.display.views.painters.OvalPainter;
import radua.ui.display.views.painters.SelectionPainter;
import radua.ui.logic.models.ISnapModel;
import radua.ui.logic.models.SnapModel;


public class OvalSnapView extends SnapView<ISnapModel>
{
	private static final long serialVersionUID = -1040008622050356472L;


	public OvalSnapView(SnapModel model) {
		super(model);
	}
	
	/** add the painter before the super ones */
	@Override
	protected void addInitialPainters() {
		_painters.add(SelectionPainter.Instance);
		_painters.add(OvalPainter.Instance);
		super.addInitialPainters();
	}
}
