package radua.servers.server.generics.ver1;

import java.net.DatagramPacket;

import radua.utils.errors.generic.ImmutableVariable;


public abstract class APacketHandler implements IPacketHandler 
{
	/*package_p*/ IPacketProvider provider;
	/*package_p*/ boolean isRunning;
	
	
	public final void setProvider(IPacketProvider nProvider) throws ImmutableVariable
	{ 
		if (provider != null) throw new ImmutableVariable(this, "provider");
		provider = nProvider; 
	}
	protected final IPacketProvider getProvider() { return provider; };

	
	public final boolean startHandler()
	{
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
		return ret;
	}
	public final boolean stopWaitHandler()
	{
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStopWait(); }
		return ret;
	}

	
	protected abstract void internalStart();
	protected abstract void internalStop();
	protected abstract void internalStopWait();
}
