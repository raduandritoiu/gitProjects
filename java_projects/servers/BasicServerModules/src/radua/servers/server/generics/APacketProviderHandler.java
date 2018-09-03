package radua.servers.server.generics;

import radua.utils.errors.generic.ImmutableVariable;

public abstract class APacketProviderHandler extends ARunnableBase implements IPacketProvider, IPacketHandler
{
	/*package_p*/ IPacketProvider provider;
	/*package_p*/ IPacketHandler handler;
	
	public final void setProvider(IPacketProvider nProvider) throws ImmutableVariable
	{ 
		if (provider != null) throw new ImmutableVariable(this, "provider");
		provider = nProvider; 
	}
	protected final IPacketProvider getProvider() { return provider; }

	
	public final void setHandler(IPacketHandler nHandler) throws ImmutableVariable
	{ 
		if (handler != null) throw new ImmutableVariable(this, "handler");
		handler = nHandler; 
	}
	protected final IPacketHandler getHandler() { return (IPacketHandler) handler; }
	
	
//===================================================================================================
//===================================================================================================

	/*package_p*/ final boolean startProvider()
	{
		// do local
		boolean ret = !isRunning;
		if (ret) { internalStart(); }
		isRunning = true;
		// bubble to provider
		((ARunnableBase) provider).startProvider();
		return ret;
	}
	/*package_p*/ final boolean stopProvider()
	{
		// bubble to provider
		((ARunnableBase) provider).stopProvider();
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStop(); }
		return ret;
	}
	/*package_p*/ final boolean stopWaitProvider()
	{
		// bubble to provider
		((ARunnableBase) provider).stopWaitProvider();
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStopWait(); }
		return ret;
	}
	
	/*package_p*/ final boolean startHandler()
	{
		// bubble to handler
		((ARunnableBase) handler).startHandler();
		// do local
		boolean ret = !isRunning;
		if (ret) { internalStart(); }
		isRunning = true;
		return ret;
	}
	/*package_p*/ final boolean stopHandler()
	{
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStop(); }
		// bubble to handler
		((ARunnableBase) handler).stopHandler();
		return ret;
	}
	/*package_p*/ final boolean stopWaitHandler()
	{
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStopWait(); }
		// bubble to handler
		((ARunnableBase) handler).stopWaitHandler();
		return ret;
	}
}
