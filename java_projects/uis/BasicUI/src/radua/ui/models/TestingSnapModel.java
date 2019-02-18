package radua.ui.models;

import java.awt.Color;

import radua.ui.models.snaps.TwoSnapPoint;

public class TestingSnapModel extends SnapModel
{
	public TestingSnapModel(int x, int y) {
		super(x, y, 80, 40, Color.GREEN);
		_snapPoints.add(new TwoSnapPoint(this, 0, 0, 0, 40));
		_snapPoints.add(new TwoSnapPoint(this, 80, 40, 80, 0));
	}
}
