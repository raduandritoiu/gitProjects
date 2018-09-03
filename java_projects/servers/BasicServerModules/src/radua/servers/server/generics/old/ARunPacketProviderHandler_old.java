package radua.servers.server.generics.old;

import radua.servers.server.generics.IRunnable;

public abstract class ARunPacketProviderHandler_old extends APacketProviderHandler_old implements IRunnable
{
	public final boolean isRunning() { return  isRunning; }
	public final boolean start()
	{
		// bubble to handler
		handler.startHandler();
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
		// bubble to handler
		handler.stopHandler();
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
		// bubble to handler
		handler.stopWaitHandler();
		return ret;
	}
}
