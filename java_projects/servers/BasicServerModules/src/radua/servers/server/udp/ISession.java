package radua.servers.server.udp;

import java.net.SocketAddress;

public interface ISession
{
	ISessionKey getKey();
	boolean poke(byte[] data, SocketAddress remoteAddr);
}
