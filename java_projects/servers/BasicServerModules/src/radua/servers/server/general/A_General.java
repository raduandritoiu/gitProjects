package radua.servers.server.general;

abstract class A_General 
{
	protected boolean isRunning;
	protected abstract void internalStart();
	protected abstract void internalStop();
	protected abstract void internalStopWait();
	
	
	/*package_p*/ boolean startProvider() { return false; }
	/*package_p*/ boolean stopProvider() { return false; }
	/*package_p*/ boolean stopWaitProvider() { return false; }
	
	/*package_p*/ boolean startHandler() { return false; }
	/*package_p*/ boolean stopHandler() { return false; }
	/*package_p*/ boolean stopWaitHandler() { return false; }
}
