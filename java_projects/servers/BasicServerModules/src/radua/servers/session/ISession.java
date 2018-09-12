package radua.servers.session;

import java.net.SocketAddress;

public interface ISession
{
	ISessionKey getKey();
	boolean poke(byte[] data, SocketAddress remoteAddr);
}
