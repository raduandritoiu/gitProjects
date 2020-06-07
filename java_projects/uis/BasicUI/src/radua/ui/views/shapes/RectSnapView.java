package radua.ui.views.shapes;

import radua.ui.display.painters.RectanglePainter;
import radua.ui.display.painters.SelectionPainter;
import radua.ui.logic.models.ISnapModel;
import radua.ui.views.SnapView;


public class RectSnapView extends SnapView<ISnapModel>
{
	private static final long serialVersionUID = 8181990344117499573L;


	public RectSnapView(ISnapModel model) {
		super(model);
	}
	
	/** add the painter before the super ones */
	@Override
	protected void addInitialPainters() {
		_painters.add(RectanglePainter.Instance);
		_painters.add(SelectionPainter.Instance);
		super.addInitialPainters();
	}
}
