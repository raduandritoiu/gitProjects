package radua.servers.server.udp;

import java.net.SocketAddress;

public interface ISession 
{
	void poke(byte[] data, SocketAddress remoteAddr);
}
