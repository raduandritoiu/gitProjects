package radua.servers.packetProcs;

import java.net.SocketAddress;

public interface IPacket 
{
	byte[] data();
	int dataLen();
	SocketAddress remoteAddr();
	PacketDirection direction();
	IPacket clone();
}
