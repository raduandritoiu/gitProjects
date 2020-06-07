package radua.ui.display.stage;

import java.awt.Color;
import java.awt.Graphics;

import radua.ui.display.insulator.MyUIComponent;


public class StageView extends MyUIComponent
{
	private static final long serialVersionUID = 1072138064506103976L;

	int x1, y1, x2, y2;
	
	
	public StageView() {
	}
	
	
	public void updateSelection(int initX, int initY, int crtX, int crtY) {
		x1 = initX;
		y1 = initY;
		x2 = crtX;
		y2 = crtY;
		repaint();
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (x1 == x2 || y1 == y2) return;
		g.setColor(Color.BLUE);
		if (x1 < x2) {
			if (y1 < y2)
				g.drawRect(x1, y1, x2-x1, y2-y1);
			else
				g.drawRect(x1, y2, x2-x1, y1-y2);
		}
		else {
			if (y1 < y2)
				g.drawRect(x2, y1, x1-x2, y2-y1);
			else
				g.drawRect(x2, y2, x1-x2, y1-y2);
		}
	}
}
