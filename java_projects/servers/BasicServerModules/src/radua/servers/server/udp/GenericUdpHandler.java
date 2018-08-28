package radua.servers.server.udp;

import java.net.DatagramPacket;

import radua.utils.errors.generic.ImmutableVariable;


public abstract class GenericUdpHandler implements IUdpHandler 
{
	IUdpNotifier notifier;
	boolean isRunning;
	
	
	public final void setNotifier(IUdpNotifier nNotifier) throws ImmutableVariable
	{ 
		if (notifier != null) throw new ImmutableVariable(this, "notifier");
		notifier = nNotifier; 
	}
	
	
	public final boolean isRunning() { return  isRunning; }
	public final boolean start()
	{
		// bubble to notifier
		notifier.startNotifier();
		// do local
		boolean ret = !isRunning;
		if (ret) { privateStart(); }
		isRunning = true;
		return ret;
	}
	public final boolean stop()
	{
		// bubble to notifier
		notifier.stopNotifier();
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { privateStop(); }
		return ret;
	}
	public final boolean stopWait()
	{
		// bubble to notifier
		notifier.stopWaitNotifier();
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { privateStopWait(); }
		return ret;
	}
	
	
	public final boolean startHandler()
	{
		boolean ret = !isRunning;
		if (ret) { privateStart(); }
		isRunning = true;
		return ret;
	}
	public final boolean stopHandler()
	{
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { privateStop(); }
		return ret;
	}
	public final boolean stopWaitHandler()
	{
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { privateStopWait(); }
		return ret;
	}

	
	protected abstract void privateStart();
	protected abstract void privateStop();
	protected abstract void privateStopWait();
	
	public abstract void handlePacket(DatagramPacket packet);
}
