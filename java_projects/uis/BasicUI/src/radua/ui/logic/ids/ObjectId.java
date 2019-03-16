package radua.ui.logic.ids;

public class ObjectId 
{
	public final int val;
	private final String type;
	
	public ObjectId(int id) {
		val = id;
		type = getClass().getName();
	}
	
	@Override
	public int hashCode() {
		return val;
	}
	
	@Override
	public boolean equals(Object id) {
		if (id == null)
			return false;
		if (!(id instanceof ObjectId))
			return false;
		if (((ObjectId) id).val != val)
			return false;
		return type.equals(id.getClass().getName());
	}
	
	@Override
	public String toString() {
		return val+"";
	}
}
