package radua.servers.server.generics.ver1;

import radua.utils.errors.generic.ImmutableVariable;


public abstract class APacketProviderHandler implements IPacketProviderHandler
{
	/*package_p*/ IPacketProvider provider;
	/*package_p*/ IPacketHandler handler;
	/*package_p*/ boolean isRunning;
	
	
	public final void setProvider(IPacketProvider nProvider) throws ImmutableVariable
	{ 
		if (provider != null) throw new ImmutableVariable(this, "provider");
		provider = nProvider; 
	}
	protected final IPacketProvider getProvider() { return provider; };
	
	
	public final void setHandler(IPacketHandler nHandler) throws ImmutableVariable
	{ 
		if (handler != null) throw new ImmutableVariable(this, "handler");
		handler = nHandler; 
	}
	protected final IPacketHandler getHandler() { return handler; };
	
	
	public final IPacketProviderHandler linkHandler(IPacketHandler handler) throws ImmutableVariable
	{
		setHandler(handler);
		handler.setProvider(this);
		return (IPacketProviderHandler) handler;
	}
	
	
	public final boolean startProvider()
	{
		// do local
		boolean ret = !isRunning;
		if (ret) { internalStart(); }
		isRunning = true;
		// bubble to provider
		provider.startProvider();
		return ret;
	}
	public final boolean stopProvider()
	{
		// bubble to provider
		provider.stopProvider();
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStop(); }
		return ret;
	}
	public final boolean stopWaitProvider()
	{
		// bubble to provider
		provider.stopWaitProvider();
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStopWait(); }
		return ret;
	}

	
	public final boolean startHandler()
	{
		// bubble to handler
		handler.startHandler();
		// do local
		boolean ret = !isRunning;
		if (ret) { internalStart(); }
		isRunning = true;
		return ret;
	}
	public final boolean stopHandler()
	{
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStop(); }
		// bubble to handler
		handler.stopHandler();
		return ret;
	}
	public final boolean stopWaitHandler()
	{
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStopWait(); }
		// bubble to handler
		handler.stopWaitHandler();
		return ret;
	}

	
	protected abstract void internalStart();
	protected abstract void internalStop();
	protected abstract void internalStopWait();
}
