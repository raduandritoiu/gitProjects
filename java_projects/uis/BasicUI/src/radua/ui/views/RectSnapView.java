package radua.ui.views;

import radua.ui.models.ISnapModel;
import radua.ui.models.SnapModel;
import radua.ui.views.painters.RectanglePainter;
import radua.ui.views.painters.SelectionPainter;


public class RectSnapView extends SnapView<ISnapModel>
{
	private static final long serialVersionUID = 8181990344117499573L;


	public RectSnapView(SnapModel model) {
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
