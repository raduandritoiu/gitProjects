package radua.errors.generic;

public class ProgrammerError extends Exception
{
	private static final long serialVersionUID = -9073531699570162913L;
	
	public ProgrammerError(String mesage)
	{
		super(mesage);
	}
}
