package radua.utils.errors.generic;

public class ImmutableVariable extends ProgrammerError 
{
	private static final long serialVersionUID = 8032636390324977609L;
	
	public ImmutableVariable(Object object, String propertyName)
	{
		super("Property \"" + propertyName + "\" from object " + 
				object.getClass().toString() + " can be initialized ONLY ONCE!" );
	}
}
