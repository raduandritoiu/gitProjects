package radua.ui.models;

import java.awt.Color;
import java.util.ArrayList;

import radua.ui.models.snaps.DrawSnapPoint;
import radua.ui.models.snaps.SnapResult;

public interface ISnapModel extends IBasicModel
{
	Color getUnsnappedColor();
	void setunsnappedColor(Color color); 
	Color getSnappedColor();
	void setSnappedColor(Color color); 

	ArrayList<DrawSnapPoint> getDrawSnapPoints();
	SnapResult snaps(SnapModel remoteSnapModel);
}
