package radua.servers.server.generics;

abstract class ARunnableBase 
{
	protected boolean isRunning;
	protected void internalStart() {}
	protected void internalStop() {}
	protected void internalStopWait() {}
	
	
	/*package_p*/ boolean startProvider() { return false; }
	/*package_p*/ boolean stopProvider() { return false; }
	/*package_p*/ boolean stopWaitProvider() { return false; }
	
	/*package_p*/ boolean startHandler() { return false; }
	/*package_p*/ boolean stopHandler() { return false; }
	/*package_p*/ boolean stopWaitHandler() { return false; }
}
