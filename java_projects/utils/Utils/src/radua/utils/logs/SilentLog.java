package radua.utils.logs;

public class SilentLog extends Log 
{
	public SilentLog() {}
	
	public void out(String str) {}
	public void err(String str) {}
	public void err(String str, Exception ex) {}
	
	
//===========================================================================================
//===========================================================================================
	
	public static void _out(String str) {}
	public static void _err(String str) {}
	public static void _err(String str, Exception ex) {}
}
