package radua.ui.views.painters.track;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

import radua.ui.models.tracks.SplitTrack;
import radua.ui.models.tracks.TrackModel;
import radua.ui.views.BasicView;
import radua.ui.views.painters.IPainter;


public class TrackPainter implements IPainter 
{
	public static final TrackPainter Instance = new TrackPainter();
	
	
	private TrackPainter() {}
	
	@Override
	public void paint(BasicView<?> view, Graphics g) {
		TrackModel model = (TrackModel) view.getModel();
		
		// set stroke
        Graphics2D g2d = null;
        Stroke tmp = null;
        if (g instanceof Graphics2D) {
            g2d = (Graphics2D) g;
            tmp = g2d.getStroke();
            g2d.setStroke(new BasicStroke(3));
        }
        
	    g.setColor(Color.BLACK);
        g.drawLine(model.firstPoint().x, model.firstPoint().y, model.middlePoint().x, model.middlePoint().y);
        
        Point black = model.secondPoint();
        Point red = null;
        if (model instanceof SplitTrack) {
        	if (((SplitTrack) model).goesRight()) {
	            black = model.secondPoint();
	            red = ((SplitTrack) model).thirdPoint();
        	}
        	else {
	            black = ((SplitTrack) model).thirdPoint();
	            red = model.secondPoint();
        	}
        }
        
        g.drawLine(model.middlePoint().x, model.middlePoint().y, black.x, black.y);
        if (red != null) {
    	    g.setColor(Color.RED);
            g.drawLine(model.middlePoint().x, model.middlePoint().y, red.x, red.y);
        }
        
		// reset stroke
        if (tmp != null) {
            g2d.setStroke(tmp);
        }
	}
}
