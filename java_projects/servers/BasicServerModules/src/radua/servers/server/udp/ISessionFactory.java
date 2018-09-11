package radua.servers.server.udp;

import java.net.SocketAddress;

public interface ISessionFactory 
{
	ISessionKey createSessionKey(byte[] data, SocketAddress remoteAddr);
	ISession createSession(byte[] data, SocketAddress remoteAddr);
	ISession createSession(ISessionKey key, byte[] data, SocketAddress remoteAddr);
}
