package radua.servers.server.general;

import radua.utils.errors.generic.ImmutableVariable;


public abstract class A_PacketHandler extends A_General implements I_PacketHandler
{
	/*package_p*/ I_PacketProvider provider;
	
	public final void setProvider(I_PacketProvider nProvider) throws ImmutableVariable
	{ 
		if (provider != null) throw new ImmutableVariable(this, "provider");
		provider = nProvider; 
	}
	protected final I_PacketProvider getProvider() { return provider; }
	
	
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
