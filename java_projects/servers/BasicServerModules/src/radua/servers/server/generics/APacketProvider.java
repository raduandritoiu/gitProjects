package radua.servers.server.generics;

import radua.utils.errors.generic.ImmutableVariable;


public abstract class APacketProvider extends ARunnableBase implements IPacketProvider
{
	/*package_p*/ IPacketHandler handler;
	
	public final void setHandler(IPacketHandler nHandler) throws ImmutableVariable
	{ 
		if (handler != null) throw new ImmutableVariable(this, "handler");
		handler = nHandler; 
	}
	protected final IPacketHandler getHandler() { return handler; }
	
	
//===================================================================================================
//===================================================================================================
	
	/*package_p*/ final boolean startProvider()
	{
		boolean ret = !isRunning;
		if (ret) { internalStart(); }
		isRunning = true;
		return ret;
	}
	/*package_p*/ final boolean stopProvider()
	{
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStop(); }
		return ret;
	}
	/*package_p*/ final boolean stopWaitProvider()
	{
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStopWait(); }
		return ret;
	}
}
