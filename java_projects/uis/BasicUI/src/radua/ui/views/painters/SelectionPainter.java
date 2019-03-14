package radua.ui.views.painters;

import java.awt.Color;
import java.awt.Graphics;

import radua.ui.views.BasicView;


public class SelectionPainter implements IPainter
{
	public static final SelectionPainter Instance = new SelectionPainter();
	
	
	private SelectionPainter() {}
	
	@Override
	public void paint(BasicView<?> view, Graphics g) {
		if (view.model().isSelected()) {
	        g.setColor(Color.BLUE);
	        g.drawRect(0, 0, view.getWidth()-1, view.getHeight()-1);
		}
	}
}