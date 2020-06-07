package radua.ui.logic.models.shapes;

import radua.ui.logic.basics.IReadablePoint;
import radua.ui.logic.basics.MColor;
import radua.ui.logic.basics.MPoint;
import radua.ui.logic.models.SnapModel;
import radua.ui.logic.models.snaps.HorizontalSnapPoint;
import radua.ui.logic.models.snaps.VerticalSnapPoint;


public class RectSnapModel extends SnapModel
{
	public RectSnapModel(IReadablePoint position) {
		this(position.x(), position.y());
	}
	public RectSnapModel(double x, double y) {
		super(x, y, 100, 50, true, MColor.GREEN, MColor.GREEN, MColor.RED);
		_snapPoints.add(new HorizontalSnapPoint(this, new MPoint(0, 25)));
		_snapPoints.add(new HorizontalSnapPoint(this, new MPoint(100, 25)));
		_snapPoints.add(new VerticalSnapPoint(this, new MPoint(50, 0)));
		_snapPoints.add(new VerticalSnapPoint(this, new MPoint(50, 50)));
	}
}
