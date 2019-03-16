package radua.ui.logic.models.snaps;

import radua.ui.logic.basics.IReadablePoint;
import radua.ui.logic.basics.MPoint;


public class SnapResultMove {
	public final boolean result;
	public final IReadablePoint translation;
	public final double rotation;
	
	public SnapResultMove(boolean nResult, IReadablePoint nTranslation, double nRotation) {
		result = nResult;
		translation = nTranslation;
		rotation = nRotation;
	}
	
	
	public static SnapResultMove NONE = new SnapResultMove(false, new MPoint(0, 0), 0);
	public static SnapResultMove NONE() { return NONE; }
}
