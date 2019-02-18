package radua.ui.models.snaps;


public class DrawSnapPoint {
	public final int x, y;
	public final boolean isSnapped;
	
	public DrawSnapPoint(int nx, int ny, boolean ns) {
		x = nx;
		y = ny;
		isSnapped = ns;
	}
}
