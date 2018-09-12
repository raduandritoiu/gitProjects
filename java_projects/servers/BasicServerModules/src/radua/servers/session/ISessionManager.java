package radua.servers.session;

import java.net.SocketAddress;

public interface ISessionManager 
{
	void addSession(ISession session);
	void removeSession(ISession session);
	void removeSession(ISessionKey sessionKey);
	ISession getSession(ISessionKey sessionKey);
	
	boolean shouldCreate(byte[] data, SocketAddress remoteAddr);
}
