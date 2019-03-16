package radua.ui.logic.models;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import radua.ui.logic.models.snaps.DrawSnapPoint;
import radua.ui.logic.models.snaps.ISnapPoint;

public interface ISnapModel extends IBasicModel
{
	Color getUnsnappedColor();
	void setunsnappedColor(Color color); 
	Color getSnappedColor();
	void setSnappedColor(Color color); 
	
	List<ISnapPoint> snapPoints();
	ArrayList<DrawSnapPoint> getDrawSnapPoints();
}
