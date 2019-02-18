package radua.ui.views.painters;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import radua.ui.views.BasicView;


public class OvalPainter implements IPainter
{
	public static OvalPainter Instance = new OvalPainter();
	
	
	private OvalPainter() {}
	
	@Override
	public void paint(BasicView<?> view, Graphics g) {
		g.setColor(view.getModel().getColor());
		g.fillOval(0, 0, view.getWidth()-1, view.getHeight()-1);
		
		if (view.getModel().isSelected()) {
			g.setColor(Color.RED);
	        Graphics2D g2d = null;
	        Stroke tmp = null;
	        if (g instanceof Graphics2D) {
	            g2d = (Graphics2D) g;
	            tmp = g2d.getStroke();
	            g2d.setStroke(new BasicStroke(3));
	        }
	        g.drawOval(1, 1, view.getWidth()-3, view.getHeight()-3);
	        if (tmp != null) {
	            g2d.setStroke(tmp);
	        }
		}
		else  {
			g.setColor(Color.BLACK);
	        g.drawOval(0, 0, view.getWidth()-1, view.getHeight()-1);
		}
	}
}
