package radua.utils.logs;

public class Log 
{
	public String prefix = "";
	public String sufix = "";
	
	public Log() {}
	public Log(String nPrefix) { prefix = nPrefix; }
	public Log(String nPrefix, String nSufix)
	{
		prefix = nPrefix;
		sufix = nSufix;
	}
	
	public void out(String str) { System.out.println(prefix + str + sufix); }
	public void err(String str) { System.out.println(str); }
	public void err(String str, Exception ex)
	{
		System.out.println(str + ". Error: " + ex);
	}
	
	
//===========================================================================================
//===========================================================================================
	
	public static void _out(String str) { System.out.println(str); }
	public static void _err(String str) { System.out.println(str); }
	public static void _err(String str, Exception ex)
	{
		System.out.println(str + ". Error: " + ex);
	}
}
