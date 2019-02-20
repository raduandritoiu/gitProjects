package radua.ui.models;

import radua.ui.common.IReadablePoint;


public interface IPolygonModel extends IBasicModel 
{
	IReadablePoint[] getPolygonPoints();
}
