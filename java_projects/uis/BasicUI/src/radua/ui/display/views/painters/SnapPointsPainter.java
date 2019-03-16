package radua.ui.display.views.painters;

import java.awt.Color;
import java.awt.Graphics;

import radua.ui.display.views.BasicView;
import radua.ui.logic.models.ISnapModel;
import radua.ui.logic.models.snaps.DrawSnapPoint;

public class SnapPointsPainter implements IPainter
{
	private static final int SNAP_SIZE = 7;
	private static final int SNAP_OFF = 4;
	
	public static final SnapPointsPainter Instance = new SnapPointsPainter();
	
	
	private SnapPointsPainter() {}

	public void paint(BasicView<?> basicView, Graphics g) {
		ISnapModel model = (ISnapModel) basicView.model();
		Color snapColor = model.getSnappedColor();
		Color unsnapColor = model.getUnsnappedColor();
		for (DrawSnapPoint snapPoint : model.getDrawSnapPoints())
		{
			if (snapPoint.isSnapped)
				g.setColor(snapColor);
			else
				g.setColor(unsnapColor);
			g.fillOval(snapPoint.x - SNAP_OFF, snapPoint.y - SNAP_OFF, SNAP_SIZE, SNAP_SIZE);
	        g.setColor(Color.BLACK);
	        g.drawOval(snapPoint.x - SNAP_OFF, snapPoint.y - SNAP_OFF, SNAP_SIZE, SNAP_SIZE);
		}
	}
}
