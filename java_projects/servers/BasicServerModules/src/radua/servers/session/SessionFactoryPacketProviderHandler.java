package radua.servers.session;

import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.basics.APacketProviderHandler;

public abstract class SessionFactoryPacketProviderHandler extends APacketProviderHandler implements ISessionFactory
{
	protected final ConcurrentHashMap<ISessionKey, ISession> sendMap;

	
	public SessionFactoryPacketProviderHandler() 
	{
		sendMap = new ConcurrentHashMap<>();
	}
	
	
	protected abstract boolean shouldCreate(byte[] data, SocketAddress remoteAddr);
	
	
	@Override
	public void handlePacket(IPacket packet) 
	{
		byte[] data = packet.data();
		SocketAddress remoteAddr = packet.remoteAddr();
		ISession session = null;
		
		// map not empty so search for session
		if (!sendMap.isEmpty()) {
			ISessionKey sessionKey = createSessionKey(data, remoteAddr);
			session = sendMap.get(sessionKey);
			// no session and may create so try to create and add it
			if (session == null && shouldCreate(data, remoteAddr)) {
				session = createSession(sessionKey, data, remoteAddr); // creates session using pre-created key
				if (session != null) {
					ISession oldSession = sendMap.putIfAbsent(session.getKey(), session);
					if (oldSession != null) session = oldSession;
				}
			}
		}
		// map is empty
		else {
			// may create session so try to create and add it
			if (session == null && shouldCreate(data, remoteAddr)) {
				session = createSession(data, remoteAddr); // creates session and key at the same time
				if (session != null) {
					ISession oldSession = sendMap.putIfAbsent(session.getKey(), session);
					if (oldSession != null) session = oldSession;
				}
			}
		}
		
		if (session != null) {
			if (session.poke(data, remoteAddr)) {
				sendMap.remove(session.getKey());
			}
		}
		else if (getHandler() != null) {
			getHandler().handlePacket(packet);
		}
	}
}
