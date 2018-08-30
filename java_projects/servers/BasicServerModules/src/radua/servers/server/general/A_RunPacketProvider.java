package radua.servers.server.general;

import radua.servers.server.generics.IRunnable;

public abstract class A_RunPacketProvider extends A_PacketProvider implements IRunnable
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
		return ret;
	}
	public final boolean stop()
	{
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
		// do local
		boolean ret = isRunning;
		isRunning = false; // reset running before stopping
		if (ret) { internalStopWait(); }
		// bubble to handler
		((A_General) handler).stopWaitHandler();
		return ret;
	}
}
