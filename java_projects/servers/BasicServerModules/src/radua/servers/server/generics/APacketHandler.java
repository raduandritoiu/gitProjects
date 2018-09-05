package radua.servers.server.generics;

import radua.utils.errors.generic.ImmutableVariable;


public abstract class APacketHandler extends A_LinkingBase implements IPacketHandler
{
	/*package_p*/ IPacketProvider provider;
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
