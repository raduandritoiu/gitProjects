package radua.servers.server.generics.old;

import java.io.IOException;
import java.net.SocketAddress;

import radua.utils.errors.generic.ImmutableVariable;


public abstract class APacketProvider_old implements IPacketProvider_old
{
	protected IPacketHandler_old handler;
	protected boolean isRunning;
	
	
	public final void setHandler(IPacketHandler_old nHandler) throws ImmutableVariable
	{ 
		if (handler != null) throw new ImmutableVariable(this, "handler");
		handler = nHandler; 
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
	
	public abstract void transmitPacket(byte[] data, SocketAddress remoteAddr) throws IOException;
}
