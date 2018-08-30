package radua.servers.server.generics;

public interface IRunnable
{
	boolean isRunning();
	boolean start();
	boolean stop();
	boolean stopWait();
}
