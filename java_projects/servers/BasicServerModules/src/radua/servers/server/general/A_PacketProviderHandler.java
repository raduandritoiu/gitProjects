package radua.servers.server.general;

import radua.utils.errors.generic.ImmutableVariable;

public abstract class A_PacketProviderHandler extends A_General implements I_PacketProvider, I_PacketHandler
{
	/*package_p*/ I_PacketProvider provider;
	/*package_p*/ I_PacketHandler handler;
	
	public final void setProvider(I_PacketProvider nProvider) throws ImmutableVariable
	{ 
		if (provider != null) throw new ImmutableVariable(this, "provider");
		provider = nProvider; 
	}
	protected final I_PacketProvider getProvider() { return provider; }

	
	public final void setHandler(I_PacketHandler nHandler) throws ImmutableVariable
	{ 
		if (handler != null) throw new ImmutableVariable(this, "handler");
		handler = nHandler; 
	}
	protected final I_PacketHandler getHandler() { return (I_PacketHandler) handler; }
	
	
//===================================================================================================
//===================================================================================================

	/*package_p*/ final boolean startProvider()
	{
		// do local
		boolean ret = !isRunning;
		if (ret) { internalStart(); }
		isRunning = true;
		// bubble to provider
		((A_General) provider).startProvider();
		return ret;
	}
	/*package_p*/ final boolean stopProvider()
	{
		// bubble to provider
		((A_General) provider).stopProvider();
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStop(); }
		return ret;
	}
	/*package_p*/ final boolean stopWaitProvider()
	{
		// bubble to provider
		((A_General) provider).stopWaitProvider();
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStopWait(); }
		return ret;
	}
	
	/*package_p*/ final boolean startHandler()
	{
		// bubble to handler
		((A_General) handler).startHandler();
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
		((A_General) handler).stopHandler();
		return ret;
	}
	/*package_p*/ final boolean stopWaitHandler()
	{
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStopWait(); }
		// bubble to handler
		((A_General) handler).stopWaitHandler();
		return ret;
	}
}
