package radua.servers.server.udp;

import java.io.IOException;
import java.net.SocketAddress;

import radua.utils.errors.generic.ImmutableVariable;

public abstract class GenericUdpNotifier implements IUdpNotifier
{
	IUdpHandler handler;
	boolean isRunning;
	
	
	public final void setHandler(IUdpHandler nHandler) throws ImmutableVariable
	{ 
		if (handler != null) throw new ImmutableVariable(this, "handler");
		handler = nHandler; 
	}
	
	
	public final boolean isRunning() { return  isRunning; }
	public final boolean start()
	{
		boolean ret = !isRunning;
		if (ret) { privateStart(); }
		isRunning = true;
		// bubble to handler
		handler.startHandler();
		return ret;
	}
	public final boolean stop()
	{
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { privateStop(); }
		// bubble to handler
		handler.stopHandler();
		return ret;
	}
	public final boolean stopWait()
	{
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { privateStopWait(); }
		// bubble to handler
		handler.stopWaitHandler();
		return ret;
	}
	
	
	public final boolean startNotifier()
	{
		boolean ret = !isRunning;
		if (ret) { privateStart(); }
		isRunning = true;
		return ret;
	}
	public final boolean stopNotifier()
	{
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { privateStop(); }
		return ret;
	}
	public final boolean stopWaitNotifier()
	{
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { privateStopWait(); }
		return ret;
	}

	
	protected abstract void privateStart();
	protected abstract void privateStop();
	protected abstract void privateStopWait();
	
	public abstract void sendPacket(byte[] data, SocketAddress remoteAddr) throws IOException;

}
