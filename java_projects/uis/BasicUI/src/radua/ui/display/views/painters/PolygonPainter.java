package radua.ui.display.views.painters;

import java.awt.Color;
import java.awt.Graphics;

import radua.ui.display.views.BasicView;
import radua.ui.logic.basics.IReadablePoint;
import radua.ui.logic.models.IPolygonModel;


public class PolygonPainter implements IPainter
{
	public static final PolygonPainter Instance = new PolygonPainter();
	
	
	private PolygonPainter() {}
	
	@Override
	public void paint(BasicView<?> view, Graphics g) {
		if (!view.model().isVisible()) 
			return;

		IReadablePoint[] points = ((IPolygonModel) view.model()).getPolygonPoints();
		int nPoints = points.length;
		int xPoints[] = new int[nPoints];
		int yPoints[] = new int[nPoints];
		for (int i = 0; i < points.length; i++) {
			xPoints[i] = points[i].intX();
			yPoints[i] = points[i].intY();
		}
		
		g.setColor(view.model().getColor());
		g.fillPolygon(xPoints, yPoints, nPoints);
        g.setColor(Color.BLACK);
        g.drawPolygon(xPoints, yPoints, nPoints);
	}
}
