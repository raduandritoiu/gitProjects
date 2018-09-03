package radua.servers.server.generics.old;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;

import radua.utils.errors.generic.ImmutableVariable;


public abstract class APacketProviderHandler_old implements IPacketHandler_old, IPacketProvider_old
{
	protected IPacketProvider_old provider;
	protected IPacketHandler_old handler;
	protected boolean isRunning;
	
	
	public final void setProvider(IPacketProvider_old nProvider) throws ImmutableVariable
	{ 
		if (provider != null) throw new ImmutableVariable(this, "provider");
		provider = nProvider; 
	}
	public final void setHandler(IPacketHandler_old nHandler) throws ImmutableVariable
	{ 
		if (handler != null) throw new ImmutableVariable(this, "handler");
		handler = nHandler; 
	}
	
	
	public final boolean startProvider()
	{
		// do local
		boolean ret = !isRunning;
		if (ret) { internalStart(); }
		isRunning = true;
		// bubble to provider
		provider.startProvider();
		return ret;
	}
	public final boolean stopProvider()
	{
		// bubble to provider
		provider.stopProvider();
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStop(); }
		return ret;
	}
	public final boolean stopWaitProvider()
	{
		// bubble to provider
		provider.stopWaitProvider();
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStopWait(); }
		return ret;
	}

	
	public final boolean startHandler()
	{
		// bubble to handler
		handler.startHandler();
		// do local
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
		// bubble to handler
		handler.stopHandler();
		return ret;
	}
	public final boolean stopWaitHandler()
	{
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStopWait(); }
		// bubble to handler
		handler.stopWaitHandler();
		return ret;
	}

	
	protected abstract void internalStart();
	protected abstract void internalStop();
	protected abstract void internalStopWait();
	
	public abstract void handlePacket(DatagramPacket packet);
	public abstract void transmitPacket(byte[] data, SocketAddress remoteAddr) throws IOException;
}
