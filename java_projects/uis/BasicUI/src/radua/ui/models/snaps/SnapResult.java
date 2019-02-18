package radua.ui.models.snaps;


public class SnapResult 
{
	public final boolean result;
	public final ISnapPoint localSnap;
	public final ISnapPoint remoteSnap;
	
	public SnapResult(boolean nResult, ISnapPoint nLocalSnap, ISnapPoint nRemoteSnap) {
		result = nResult;
		localSnap = nLocalSnap;
		remoteSnap = nRemoteSnap;
	}
	
	public SnapResultMove getMove() {
		if (result) {
			return localSnap.getSnapMove(remoteSnap);
		}
		return null;
	}
	
	
	private static SnapResult FALSE_RESULT = new SnapResult(false, null, null);
	
	public static SnapResult FALSE() {
		return FALSE_RESULT;
	}
		
	public static SnapResult TRUE(ISnapPoint localSnap, ISnapPoint remoteSnap) {
		return new SnapResult(true, localSnap, remoteSnap);
	}
}
