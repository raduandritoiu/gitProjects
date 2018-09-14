package radua.servers.packetProcs.linking;

import radua.servers.packetProcs.IRunnable;

public abstract class ARunPacketHandlerProvider extends APacketHandlerProvider implements IRunnable
{
	public final boolean isRunning() { return  isRunning; }
	public final boolean start() { return super.pp_start(); }
	public final boolean stop() { return super.pp_stop(); }
	public final boolean stopWait() { return super.pp_stopWait(); }
}
