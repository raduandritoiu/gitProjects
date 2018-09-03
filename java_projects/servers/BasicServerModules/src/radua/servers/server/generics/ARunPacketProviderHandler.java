package radua.servers.server.generics;

public abstract class ARunPacketProviderHandler extends APacketProviderHandler
{
	public final boolean isRunning() { return  isRunning; }
	public final boolean start()
	{
		// bubble to handler
		((ARunnableBase) handler).startHandler();
		// do local
		boolean ret = !isRunning;
		if (ret) { internalStart(); }
		isRunning = true;
		// bubble to provider
		((ARunnableBase) provider).startProvider();
		return ret;
	}
	public final boolean stop()
	{
		// bubble to provider
		((ARunnableBase) provider).stopProvider();
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStop(); }
		// bubble to handler
		((ARunnableBase) handler).stopHandler();
		return ret;
	}
	public final boolean stopWait()
	{
		// bubble to provider
		((ARunnableBase) provider).stopWaitProvider();
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStopWait(); }
		// bubble to handler
		((ARunnableBase) handler).stopWaitHandler();
		return ret;
	}
}
