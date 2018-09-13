package radua.utils.errors.generic;

public class UniqueKeyValue extends ProgrammerError 
{
	private static final long serialVersionUID = 6500862688126881025L;

	public UniqueKeyValue(Object key, Object value) 
	{
		super("The key < " + key.hashCode() + " : " + key.toString() + "> attached to the object value < " 
				+ value.hashCode() + " : " + value.toString() + " > already exists!");
	}
}
