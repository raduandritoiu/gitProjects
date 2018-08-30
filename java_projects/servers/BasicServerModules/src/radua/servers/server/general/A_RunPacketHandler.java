package radua.servers.server.general;

import radua.servers.server.generics.IRunnable;

public abstract class A_RunPacketHandler extends A_PacketHandler implements IRunnable
{
	public final boolean isRunning() { return  isRunning; }
	public final boolean start()
	{
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
		return ret;
	}
}
