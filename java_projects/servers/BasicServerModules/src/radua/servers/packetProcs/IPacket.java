package radua.servers.packetProcs;

import java.net.SocketAddress;

public interface IPacket 
{
	int dataLen();
	byte[] data();
	SocketAddress remoteAddr();
}
