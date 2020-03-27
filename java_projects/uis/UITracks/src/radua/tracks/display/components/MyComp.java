package radua.tracks.display.components;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

public class MyComp extends JComponent
{
	private static final long serialVersionUID = 1635594734129921987L;
	
	public final String name;
	private Color bgColor;
	private Color frameColor;
	private int pos;
	
	public MyComp(String nName, Color nBgColor) {
		this(nName, nBgColor, 0, false, Color.BLACK);
	}
	public MyComp(String nName, Color nBgColor, int nPos) {
		this(nName, nBgColor, nPos, false, Color.BLACK);
	}
	public MyComp(String nName, Color nBgColor, int nPos, boolean layout) {
		this(nName, nBgColor, nPos, layout, Color.BLACK);
	}
	public MyComp(String nName, Color nBgColor, int nPos, boolean layout, Color nFrameColor) {
		name = nName;
		bgColor = nBgColor;
		frameColor = nFrameColor;
		pos = nPos;
		if (!layout) 
			setLayout(null);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(bgColor);
		int w = getWidth();
		int h = getHeight();
		if (pos == 1) {
			g.fillRect(0, 0, w/2, h);
			g.fillRect(w/2, 0, w, h/2);
		}
		else if (pos == 2) {
			g.fillRect(w/2, 0, w/2, h);
			g.fillRect(0, h/2, w/2, h);
		}
		
		g.setColor(frameColor);
		
		if (pos == 1) {
			g.drawRect(0, 0, w/2-1, h-1);
			g.drawRect(w/2, 0, w-1, h/2-1);
		}
		else if (pos == 2) {
			g.drawRect(w/2, 0, w/2-1, h-1);
			g.drawRect(0, h/2, w/2-1, h-1);
		}
	}
}
