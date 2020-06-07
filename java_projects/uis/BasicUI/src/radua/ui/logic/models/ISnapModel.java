package radua.ui.logic.models;

import java.util.ArrayList;
import java.util.List;

import radua.ui.logic.basics.MColor;
import radua.ui.logic.models.snaps.DrawSnapPoint;
import radua.ui.logic.models.snaps.ISnapPoint;

public interface ISnapModel extends IBasicModel
{
	MColor getUnsnappedColor();
	void setunsnappedColor(MColor color); 
	MColor getSnappedColor();
	void setSnappedColor(MColor color); 
	
	List<ISnapPoint> snapPoints();
	ArrayList<DrawSnapPoint> getDrawSnapPoints();
}
