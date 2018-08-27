package radua.servers.server.interfaces;

import java.net.SocketAddress;

public interface IGenericServer 
{
	public static final int MAX_PDU = 200;
	
	
	SocketAddress getLocalAddr();
	boolean isRunning();
	
	boolean start();
	boolean stop();
	boolean stopWait();
}
