package radua.servers.server.generics;

public abstract class ARunPacketProvider extends APacketProvider implements IRunnable
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
		return ret;
	}
	public final boolean stop()
	{
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
