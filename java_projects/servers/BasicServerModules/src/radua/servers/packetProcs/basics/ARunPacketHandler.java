package radua.servers.packetProcs.basics;

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
		return ret;
	}
}
