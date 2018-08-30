package radua.servers.server.general;

import radua.utils.errors.generic.ImmutableVariable;


public abstract class A_PacketProvider extends A_General implements I_PacketProvider
{
	/*package_p*/ I_PacketHandler handler;
	
	public final void setHandler(I_PacketHandler nHandler) throws ImmutableVariable
	{ 
		if (handler != null) throw new ImmutableVariable(this, "handler");
		handler = nHandler; 
	}
	protected final I_PacketHandler getHandler() { return handler; }
	
	
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
