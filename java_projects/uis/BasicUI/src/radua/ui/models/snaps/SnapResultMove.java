package radua.ui.models.snaps;

import java.awt.Point;


public class SnapResultMove {
	public final boolean result;
	public final Point translation;
	public final double rotation;
	
	public SnapResultMove(boolean nResult, Point nTranslation, double nRotation) {
		result = nResult;
		translation = nTranslation;
		rotation = nRotation;
	}
	
	
	public static SnapResultMove NONE = new SnapResultMove(false, new Point(0, 0), 0);
	public static SnapResultMove NONE() { return NONE; }
}
