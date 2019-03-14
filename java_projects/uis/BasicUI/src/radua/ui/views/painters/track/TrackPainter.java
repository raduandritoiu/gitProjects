package radua.ui.views.painters.track;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import radua.ui.common.IReadablePoint;
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
            g2d.setStroke(new BasicStroke(2));
        }
        
	    g.setColor(Color.BLACK);
        g.drawLine(model.firstPoint().intX(), model.firstPoint().intY(), model.middlePoint().intX(), model.middlePoint().intY());
        
        IReadablePoint black = model.secondPoint();
        IReadablePoint red = null;
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
        
        g.drawLine(model.middlePoint().intX(), model.middlePoint().intY(), black.intX(), black.intY());
        if (red != null) {
    	    g.setColor(Color.GRAY);
            g.drawLine(model.middlePoint().intX(), model.middlePoint().intY(), red.intX(), red.intY());
        }
        
		// reset stroke
        if (tmp != null) {
            g2d.setStroke(tmp);
        }
	}
}
