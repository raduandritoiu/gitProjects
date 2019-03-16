package radua.ui.logic.ids;

import java.util.concurrent.atomic.AtomicInteger;

public class IdManager 
{
//	private static final AtomicInteger genCnt = new AtomicInteger(0);
	private static final AtomicInteger modelCnt = new AtomicInteger(0);
	private static final AtomicInteger viewCnt = new AtomicInteger(0);
	private static final AtomicInteger snapCnt = new AtomicInteger(0);
	
	
	public static ModelId GetModelId() {
		return new ModelId(modelCnt.incrementAndGet());
	}
	
	public static ViewId GetViewId() {
		return new ViewId(viewCnt.incrementAndGet());
	}
	
	public static SnapId GetSnapId() {
		return new SnapId(snapCnt.incrementAndGet());
	}
}
