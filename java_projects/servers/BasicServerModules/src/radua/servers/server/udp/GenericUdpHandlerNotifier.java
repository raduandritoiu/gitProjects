package radua.servers.server.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;

import radua.utils.errors.generic.ImmutableVariable;

public abstract class GenericUdpHandlerNotifier implements IUdpHandler, IUdpNotifier
{
	IUdpNotifier notifier;
	IUdpHandler handler;
	boolean isRunning;
	
	
	public final void setNotifier(IUdpNotifier nNotifier) throws ImmutableVariable
	{ 
		if (notifier != null) throw new ImmutableVariable(this, "notifier");
		notifier = nNotifier; 
	}
	public final void setHandler(IUdpHandler nHandler) throws ImmutableVariable
	{ 
		if (handler != null) throw new ImmutableVariable(this, "handler");
		handler = nHandler; 
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
		// bubble to handler
		handler.startHandler();
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
		// bubble to handler
		handler.stopHandler();
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
		// bubble to handler
		handler.stopWaitHandler();
		return ret;
	}
	
	
	public final boolean startNotifier()
	{
		// bubble to notifier
		notifier.startNotifier();
		// do local
		boolean ret = !isRunning;
		if (ret) { privateStart(); }
		isRunning = true;
		return ret;
	}
	public final boolean stopNotifier()
	{
		// bubble to notifier
		notifier.stopNotifier();
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { privateStop(); }
		return ret;
	}
	public final boolean stopWaitNotifier()
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
		// do local
		boolean ret = !isRunning;
		if (ret) { privateStart(); }
		isRunning = true;
		// bubble to handler
		handler.startHandler();
		return ret;
	}
	
	public final boolean stopHandler()
	{
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { privateStop(); }
		// bubble to handler
		handler.stopHandler();
		return ret;
	}
	public final boolean stopWaitHandler()
	{
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { privateStopWait(); }
		// bubble to handler
		handler.stopWaitHandler();
		return ret;
	}

	
	protected abstract void privateStart();
	protected abstract void privateStop();
	protected abstract void privateStopWait();
	
	public abstract void handlePacket(DatagramPacket packet);
	public abstract void sendPacket(byte[] data, SocketAddress remoteAddr) throws IOException;
}
