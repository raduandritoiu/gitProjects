package radua.ui.ids;

import java.util.concurrent.atomic.AtomicInteger;

public class IdManager {

	private static final AtomicInteger cnt = new AtomicInteger(0);
	
	
	public static ModelId GetModelId() {
		return new ModelId(cnt.incrementAndGet());
	}
	
	public static ViewId GetViewId() {
		return new ViewId(cnt.incrementAndGet());
	}
	
	public static SnapId GetSnapId() {
		return new SnapId(cnt.incrementAndGet());
	}
}
