package radua.ui.display.painters;

import java.awt.Graphics;

import radua.ui.views.BasicView;

public interface IPainter
{
	void paint(BasicView<?> view, Graphics g);
}
