package radua.servers.server.generics;

import radua.utils.errors.generic.ImmutableVariable;


public abstract class APacketProviderHandler extends A_LinkingBase implements IPacketProviderHandler
{
	/*package_p*/ IPacketProvider provider;
	/*package_p*/ IPacketHandler handler;
	/*package_p*/ boolean isRunning;

	public final IPacketProviderHandler linkProvider(IPacketProvider provider) throws ImmutableVariable
	{
		provider.setHandler(this);
		setProvider(provider);
		if (provider instanceof IPacketProviderHandler)
			return (IPacketProviderHandler) provider;
		return null;
	}
	public final void setProvider(IPacketProvider nProvider) throws ImmutableVariable
	{ 
		if (provider != null) throw new ImmutableVariable(this, "provider");
		provider = nProvider; 
		if (isRunning && provider != null && provider instanceof A_LinkingBase)
			((A_LinkingBase) provider).startProvider();
	}
	protected final IPacketProvider getProvider() { return provider; }

	
	public final IPacketProviderHandler linkHandler(IPacketHandler handler) throws ImmutableVariable
	{
		handler.setProvider(this);
		setHandler(handler);
		if (handler instanceof IPacketProviderHandler)
			return (IPacketProviderHandler) handler;
		return null;
	}
	public final void setHandler(IPacketHandler nHandler) throws ImmutableVariable
	{ 
		if (handler != null) throw new ImmutableVariable(this, "handler");
		handler = nHandler; 
		if (isRunning && handler != null && handler instanceof A_LinkingBase)
			((A_LinkingBase) handler).startHandler();
	}
	protected final IPacketHandler getHandler() { return handler; }
	
	
//===================================================================================================
//===================================================================================================

	/*package_p*/ final boolean startProvider()
	{
		// do local
		boolean ret = !isRunning;
		if (ret) { internalStart(); }
		isRunning = true;
		// bubble to provider
		if (provider != null && provider instanceof A_LinkingBase)
			((A_LinkingBase) provider).startProvider();
		return ret;
	}
	/*package_p*/ final boolean stopProvider()
	{
		// bubble to provider
		if (provider != null && provider instanceof A_LinkingBase)
			((A_LinkingBase) provider).stopProvider();
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStop(); }
		return ret;
	}
	/*package_p*/ final boolean stopWaitProvider()
	{
		// bubble to provider
		if (provider != null && provider instanceof A_LinkingBase)
			((A_LinkingBase) provider).stopWaitProvider();
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStopWait(); }
		return ret;
	}
	
	/*package_p*/ final boolean startHandler()
	{
		// bubble to handler
		if (handler != null && handler instanceof A_LinkingBase)
			((A_LinkingBase) handler).startHandler();
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
		if (handler != null && handler instanceof A_LinkingBase)
			((A_LinkingBase) handler).stopHandler();
		return ret;
	}
	/*package_p*/ final boolean stopWaitHandler()
	{
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStopWait(); }
		// bubble to handler
		if (handler != null && handler instanceof A_LinkingBase)
			((A_LinkingBase) handler).stopWaitHandler();
		return ret;
	}
}
