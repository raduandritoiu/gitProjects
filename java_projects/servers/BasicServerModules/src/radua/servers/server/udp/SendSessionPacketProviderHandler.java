package radua.servers.server.udp;

import java.io.IOException;
import java.net.SocketAddress;

import radua.servers.packetProcs.GenericPacket;
import radua.servers.packetProcs.IPacket;
import radua.servers.session.ISession;
import radua.servers.session.ISessionKey;
import radua.servers.session.SessionFactoryPacketProviderHandler;

public class SendSessionPacketProviderHandler extends SessionFactoryPacketProviderHandler
{
	
	public ISessionKey createSessionKey(byte[] data, SocketAddress remoteAddr) { return null; }
	public ISession createSession(byte[] data, SocketAddress remoteAddr) { return null; }
	public ISession createSession(ISessionKey key, byte[] data, SocketAddress remoteAddr) { return null; }
	public boolean shouldCreate(byte[] data, SocketAddress remoteAddr) { return false; }

	
	public ISession send(byte[] data, SocketAddress remoteAddr, boolean hasReplay) throws IOException
	{
		GenericPacket packet = new GenericPacket(data.length, data, remoteAddr);
		if (!hasReplay) {
			transmitPacket(packet);
			return null;
		}
		
		ISession session = createSession(data, remoteAddr);
		ISession oldSession = sendMap.putIfAbsent(session.getKey(), session);
		if (oldSession != null) session = oldSession;
		
		getProvider().transmitPacket(packet);
		return session;
	}
	
	
	@Override
	public void transmitPacket(IPacket packet) throws IOException
	{
		getProvider().transmitPacket(packet);
	}
}
