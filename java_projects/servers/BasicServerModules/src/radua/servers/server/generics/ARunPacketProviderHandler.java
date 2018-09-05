package radua.servers.server.generics;

public abstract class ARunPacketProviderHandler extends APacketProviderHandler implements IRunnable
{
	public final boolean isRunning() { return  isRunning; }
	public final boolean start()
	{
		// bubble to handler
		if (handler != null && handler instanceof A_LinkingBase)
			((A_LinkingBase) handler).startHandler();
		// do local
		boolean ret = !isRunning;
		if (ret) { internalStart(); }
		isRunning = true;
		// bubble to provider
		if (provider != null && provider instanceof A_LinkingBase)
			((A_LinkingBase) provider).startProvider();
		return ret;
	}
	public final boolean stop()
	{
		// bubble to provider
		if (provider != null && provider instanceof A_LinkingBase)
			((A_LinkingBase) provider).stopProvider();
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStop(); }
		// bubble to handler
		if (handler != null && handler instanceof A_LinkingBase)
			((A_LinkingBase) handler).stopHandler();
		return ret;
	}
	public final boolean stopWait()
	{
		// bubble to provider
		if (provider != null && provider instanceof A_LinkingBase)
			((A_LinkingBase) provider).stopWaitProvider();
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStopWait(); }
		// bubble to handler
		if (handler != null && handler instanceof A_LinkingBase)
			((A_LinkingBase) handler).stopWaitHandler();
		return ret;
	}
}
