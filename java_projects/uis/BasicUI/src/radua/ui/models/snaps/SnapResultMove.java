package radua.ui.models.snaps;

import radua.ui.common.IReadablePoint;
import radua.ui.common.MPoint;


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
