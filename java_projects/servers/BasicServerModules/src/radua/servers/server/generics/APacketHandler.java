package radua.servers.server.generics;

import radua.utils.errors.generic.ImmutableVariable;


public abstract class APacketHandler extends ARunnableBase implements IPacketHandler
{
	/*package_p*/ IPacketProvider provider;
	
	public final void setProvider(IPacketProvider nProvider) throws ImmutableVariable
	{ 
		if (provider != null) throw new ImmutableVariable(this, "provider");
		provider = nProvider; 
	}
	protected final IPacketProvider getProvider() { return provider; }
	
	
//===================================================================================================
//===================================================================================================
	
	/*package_p*/ final boolean startHandler()
	{
		boolean ret = !isRunning;
		if (ret) { internalStart(); }
		isRunning = true;
		return ret;
	}
	/*package_p*/ final boolean stopHandler()
	{
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStop(); }
		return ret;
	}
	/*package_p*/ final boolean stopWaitHandler()
	{
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStopWait(); }
		return ret;
	}
}
