package radua.servers.server;

import java.net.SocketAddress;

import radua.servers.packetProcs.IRunnable;

public interface IServer extends IRunnable 
{
	public static final int MAX_PDU = 200;
	
	
	SocketAddress getLocalAddr();
}
