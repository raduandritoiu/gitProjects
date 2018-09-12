package radua.servers.vers1.server.generics;

import radua.utils.errors.generic.ImmutableVariable;


public abstract class APacketProvider implements IPacketProvider
{
	/*package_p*/ IPacketHandler handler;
	/*package_p*/ boolean isRunning;
	
	
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
		boolean ret = !isRunning;
		if (ret) { internalStart(); }
		isRunning = true;
		return ret;
	}
	public final boolean stopProvider()
	{
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStop(); }
		return ret;
	}
	public final boolean stopWaitProvider()
	{
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStopWait(); }
		return ret;
	}

	
	protected abstract void internalStart();
	protected abstract void internalStop();
	protected abstract void internalStopWait();
}
