package radua.servers.server.generics;

import java.net.SocketAddress;

public interface IServer extends IRunnable 
{
	public static final int MAX_PDU = 200;
	
	
	SocketAddress getLocalAddr();
}
