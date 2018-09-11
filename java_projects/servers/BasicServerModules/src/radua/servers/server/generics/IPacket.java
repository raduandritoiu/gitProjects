package radua.servers.server.generics;

import java.net.SocketAddress;

public interface IPacket 
{
	int getDataLen();
	byte[] getData();
	SocketAddress getRemoteAddr();
}
