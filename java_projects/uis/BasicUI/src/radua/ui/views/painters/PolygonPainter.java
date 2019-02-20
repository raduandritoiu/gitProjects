package radua.ui.views.painters;

import java.awt.Color;
import java.awt.Graphics;

import radua.ui.common.IReadablePoint;
import radua.ui.models.IPolygonModel;
import radua.ui.views.BasicView;


public class PolygonPainter implements IPainter
{
	public static final PolygonPainter Instance = new PolygonPainter();
	
	
	private PolygonPainter() {}
	
	@Override
	public void paint(BasicView<?> view, Graphics g) {
		IReadablePoint[] points = ((IPolygonModel) view.getModel()).getPolygonPoints();
		int nPoints = points.length;
		int xPoints[] = new int[nPoints];
		int yPoints[] = new int[nPoints];
		for (int i = 0; i < points.length; i++) {
			xPoints[i] = points[i].intX();
			yPoints[i] = points[i].intY();
		}
		
		g.setColor(view.getModel().getColor());
		g.fillPolygon(xPoints, yPoints, nPoints);
        g.setColor(Color.BLACK);
        g.drawPolygon(xPoints, yPoints, nPoints);
	}
}
