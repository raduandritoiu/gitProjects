package radua.ui.views;

import radua.ui.models.ISnapModel;
import radua.ui.models.SnapModel;
import radua.ui.views.painters.OvalPainter;
import radua.ui.views.painters.SelectionPainter;


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
