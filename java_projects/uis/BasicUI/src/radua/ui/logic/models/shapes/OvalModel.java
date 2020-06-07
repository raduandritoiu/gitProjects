package radua.ui.logic.models.shapes;

import radua.ui.logic.basics.IReadablePoint;
import radua.ui.logic.basics.IReadableSize;
import radua.ui.logic.basics.MColor;
import radua.ui.logic.models.BasicModel;


public class OvalModel extends BasicModel
{
	public OvalModel(IReadablePoint position, IReadableSize size) {
		this(position.x(), position.y(), size.width(), size.height());
	}
	public OvalModel(double x, double y, double width, double height) {
		super(x, y, width, height, true, MColor.PINK);
	}
}
