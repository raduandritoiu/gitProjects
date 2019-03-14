package radua.ui.views.painters;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import radua.ui.views.BasicView;


public class RectanglePainter implements IPainter
{
	public static final RectanglePainter Instance = new RectanglePainter();
	
	
	private RectanglePainter() {}
	
	@Override
	public void paint(BasicView<?> view, Graphics g) {
		g.setColor(view.model().getColor());
		g.fillRect(0, 0, view.getWidth(), view.getHeight());
		
		if (view.model().isSelected()) {
			g.setColor(Color.RED);
	        Graphics2D g2d = null;
	        Stroke tmp = null;
	        if (g instanceof Graphics2D) {
	            g2d = (Graphics2D) g;
	            tmp = g2d.getStroke();
	            g2d.setStroke(new BasicStroke(3));
	        }
	        g.drawRect(1, 1, view.getWidth()-3, view.getHeight()-3);
	        if (tmp != null) {
	            g2d.setStroke(tmp);
	        }
		}
		else  {
	        g.setColor(Color.BLACK);
	        g.drawRect(0, 0, view.getWidth()-1, view.getHeight()-1);
		}
	}
}
