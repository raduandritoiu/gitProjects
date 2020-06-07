package radua.ui.views.shapes;

import radua.ui.display.painters.OvalPainter;
import radua.ui.display.painters.SelectionPainter;
import radua.ui.logic.models.ISnapModel;
import radua.ui.views.SnapView;


public class OvalSnapView extends SnapView<ISnapModel>
{
	private static final long serialVersionUID = -1040008622050356472L;


	public OvalSnapView(ISnapModel model) {
		super(model);
	}
	
	/** add the painter before the super ones */
	@Override
	protected void addInitialPainters() {
		_painters.add(OvalPainter.Instance);
		_painters.add(SelectionPainter.Instance);
		super.addInitialPainters();
	}
}
