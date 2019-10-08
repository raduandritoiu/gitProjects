/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siemens.vsst.data.parsers.utils;


/**
 *
 * @author radua
 */
public class VSSTLogger 
{
	/** This is a serious error.
	 * @param errStr 
	 */
	public static void Error(String errStr) {
		System.out.println(errStr);
	}
	
	
	/** This is for printing status info of the application progress.
	 * @param infoStr 
	 */
	public static void Info(String infoStr) {
		System.out.println(infoStr);		
	}
	
	
	/** This is more of a warning that something is odd/wrong, probably some of the input data has errors, 
	 * missing info, not very important ones. The application can continue in this state.
	 * @param errStr 
	 */
	public static void Warning(String errStr) {
		System.out.println(errStr);
	}
		
	
	/** This is an option level for testing and debugging
	 * @param logStr 
	 */
	public static void Log(String logStr) {
		System.out.println(logStr);			
	}
	
	
	/** This is an option level for testing and debugging
	 * @param testStr 
	 */
	public static void Testing(String testStr) {
//		System.out.println(testStr);
	}
	
	
	/** This is an option level for testing and debugging
	 * @param testStr 
	 */
	public static void TestComponent(String testStr) {
//		System.out.println(testStr);
	}
	
	
	/** This is an option level for testing and debugging
	 * @param debugStr 
	 */
	public static void Debug(String debugStr) {
		System.out.println(debugStr);		
	}
	
	
	public static void open_file() {
	  
	}
}