package radua.ui.display.views;

import radua.ui.display.views.painters.RectanglePainter;
import radua.ui.display.views.painters.SelectionPainter;
import radua.ui.logic.models.ISnapModel;


public class RectSnapView extends SnapView<ISnapModel>
{
	private static final long serialVersionUID = 8181990344117499573L;


	public RectSnapView(ISnapModel model) {
		super(model);
	}
	
	/** add the painter before the super ones */
	@Override
	protected void addInitialPainters() {
		_painters.add(SelectionPainter.Instance);
		_painters.add(RectanglePainter.Instance);
		super.addInitialPainters();
	}
}
