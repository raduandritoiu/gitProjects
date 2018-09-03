package radua.servers.server.generics;

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
		return ret;
	}
}
