package radua.servers.server.general;

public abstract class A_RunPacketProviderHandler extends A_PacketProviderHandler
{
	public final boolean isRunning() { return  isRunning; }
	public final boolean start()
	{
		// bubble to handler
		((A_General) handler).startHandler();
		// do local
		boolean ret = !isRunning;
		if (ret) { internalStart(); }
		isRunning = true;
		// bubble to provider
		((A_General) provider).startProvider();
		return ret;
	}
	public final boolean stop()
	{
		// bubble to provider
		((A_General) provider).stopProvider();
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStop(); }
		// bubble to handler
		((A_General) handler).stopHandler();
		return ret;
	}
	public final boolean stopWait()
	{
		// bubble to provider
		((A_General) provider).stopWaitProvider();
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStopWait(); }
		// bubble to handler
		((A_General) handler).stopWaitHandler();
		return ret;
	}
}
