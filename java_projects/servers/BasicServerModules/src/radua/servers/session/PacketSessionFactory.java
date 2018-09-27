package radua.servers.session;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.linking.APacketMiddle_SS;
import radua.utils.errors.generic.UniqueKeyValue;


public abstract class PacketSessionFactory extends APacketMiddle_SS implements ISessionFactory, ISessionManager
{
	protected final ConcurrentHashMap<ISessionKey, ISession> sendMap = new ConcurrentHashMap<>();

	
	public ISession addNewSession(IPacket packet) 
	{
		// creates session and key at the same time
		ISession session = createSession(packet);
		if (session != null) {
			ISession oldSession = sendMap.putIfAbsent(session.key(), session);
			if (oldSession != null) session = oldSession;
		}
		return session;
	}
	
	public ISession addNewSession(ISessionKey sessionKey, IPacket packet) 
	{
		// creates session using pre-created key
		ISession session = createSession(sessionKey, packet);
		if (session != null) {
			ISession oldSession = sendMap.putIfAbsent(session.key(), session);
			if (oldSession != null) session = oldSession;
		}
		return session;
	}
	
	public ISession addUniqueSession(IPacket packet) throws UniqueKeyValue 
	{
		// creates session and key at the same time
		ISession session = createSession(packet);
		if (session != null) {
			ISession oldSession = sendMap.putIfAbsent(session.key(), session);
			if (oldSession != null) throw new UniqueKeyValue(session.key(), session);
		}
		return session;
	}
	
	public ISession addUniqueSession(ISessionKey sessionKey, IPacket packet) throws UniqueKeyValue 
	{
		// creates session using pre-created key
		ISession session = createSession(sessionKey, packet);
		if (session != null) {
			ISession oldSession = sendMap.putIfAbsent(session.key(), session);
			if (oldSession != null) throw new UniqueKeyValue(session.key(), session);
		}
		return session;
	}
	
	public ISession removeSession(ISessionKey sessionKey) 
	{
		return sendMap.remove(sessionKey);
	}
	
	public ISession getSession(ISessionKey sessionKey) 
	{
		return sendMap.get(sessionKey);
	}
	
	
	@Override
	public boolean handlePacket(IPacket packet)
	{
		ISession session = null;
		boolean handled = false;
		
		// map not empty so search for session
		if (!sendMap.isEmpty()) {
			ISessionKey sessionKey = createSessionKey(packet);
			session = sendMap.get(sessionKey);
			// no session and may create so try to create and add it
			if (session == null && shouldCreate(packet)) {
				// creates and adds a session using pre-created key 
				session = createSession(sessionKey, packet);
				if (session != null) {
					ISession oldSession = sendMap.putIfAbsent(session.key(), session);
					if (oldSession != null) session = oldSession;
				}
			}
		}
		// map is empty
		else {
			// may create session so try to create and add it
			if (session == null && shouldCreate(packet)) {
				// creates and adds a session and key at the same time
				session = createSession(packet); 
				if (session != null) {
					ISession oldSession = sendMap.putIfAbsent(session.key(), session);
					if (oldSession != null) session = oldSession;
				}
			}
		}
		
		if (session != null) {
			handled = true;
			if (session.handlePacket(packet)) {
				sendMap.remove(session.key());
			}
		}
		else if (getInner() != null) {
			handled = getInner().handlePacket(packet);
		}
		
		return handled;
	}
	
	
	@Override
	public boolean transmitPacket(IPacket packet) throws IOException
	{
		return getOuter().transmitPacket(packet);
	}
}
