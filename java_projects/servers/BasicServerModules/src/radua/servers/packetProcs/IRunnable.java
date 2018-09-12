package radua.servers.packetProcs;

public interface IRunnable
{
	boolean isRunning();
	boolean start();
	boolean stop();
	boolean stopWait();
}
