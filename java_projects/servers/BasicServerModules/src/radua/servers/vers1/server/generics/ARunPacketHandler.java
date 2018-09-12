package radua.servers.vers1.server.generics;

import radua.servers.packetProcs.IRunnable;

public abstract class ARunPacketHandler extends APacketHandler implements IRunnable
{
	public final boolean isRunning() { return  isRunning; }
	public final boolean start()
	{
		// do local
		boolean ret = !isRunning;
		if (ret) { internalStart(); }
		isRunning = true;
		// bubble to provider
		provider.startProvider();
		return ret;
	}
	public final boolean stop()
	{
		// bubble to provider
		provider.stopProvider();
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStop(); }
		return ret;
	}
	public final boolean stopWait()
	{
		// bubble to provider
		provider.stopWaitProvider();
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStopWait(); }
		return ret;
	}
}
