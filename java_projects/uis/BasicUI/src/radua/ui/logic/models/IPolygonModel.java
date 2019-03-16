package radua.ui.logic.models;

import radua.ui.logic.basics.IReadablePoint;


public interface IPolygonModel extends IBasicModel 
{
	IReadablePoint[] getPolygonPoints();
}
