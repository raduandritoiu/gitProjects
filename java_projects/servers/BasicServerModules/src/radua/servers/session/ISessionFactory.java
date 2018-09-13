package radua.servers.session;

import radua.servers.packetProcs.IPacket;

public interface ISessionFactory 
{
	boolean shouldCreate(IPacket packet);
	ISessionKey createSessionKey(IPacket packet);
	ISession createSession(IPacket packet);
	ISession createSession(ISessionKey key, IPacket packet);
}
