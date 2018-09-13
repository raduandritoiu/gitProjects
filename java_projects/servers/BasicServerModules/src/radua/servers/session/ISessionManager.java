package radua.servers.session;

import radua.servers.packetProcs.IPacket;
import radua.utils.errors.generic.UniqueKeyValue;

public interface ISessionManager 
{
	ISession addNewSession(IPacket packet);
	ISession addNewSession(ISessionKey session, IPacket packet);
	ISession addUniqueSession(IPacket packet) throws UniqueKeyValue;
	ISession addUniqueSession(ISessionKey sessionKey, IPacket packet) throws UniqueKeyValue;
	
	ISession removeSession(ISessionKey sessionKey);
	ISession getSession(ISessionKey sessionKey);
}
