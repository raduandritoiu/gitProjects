package radua.servers.session;

import radua.servers.packetProcs.IPacket;

public interface ISession
{
	ISessionKey key();
	boolean handlePacket(IPacket packet);
}
