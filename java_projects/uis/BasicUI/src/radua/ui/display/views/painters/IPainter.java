package radua.ui.display.views.painters;

import java.awt.Graphics;

import radua.ui.display.views.BasicView;

public interface IPainter
{
	void paint(BasicView<?> view, Graphics g);
}
