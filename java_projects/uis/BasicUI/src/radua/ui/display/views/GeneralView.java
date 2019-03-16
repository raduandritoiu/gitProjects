package radua.ui.display.views;

import radua.ui.display.views.painters.PolygonPainter;
import radua.ui.display.views.painters.SelectionPainter;
import radua.ui.display.views.painters.SnapPointsPainter;
import radua.ui.logic.models.IGeneralModel;


public class GeneralView<GM extends IGeneralModel> extends SnapView<GM>
{
	private static final long serialVersionUID = -4970469429078861876L;
	
	
	public GeneralView(GM model) {
		super(model);
	}

	/** add the painter before the super ones */
	@Override
	protected void addInitialPainters() {
		_painters.add(SelectionPainter.Instance);
		_painters.add(PolygonPainter.Instance);
		_painters.add(SnapPointsPainter.Instance);
	}
}
