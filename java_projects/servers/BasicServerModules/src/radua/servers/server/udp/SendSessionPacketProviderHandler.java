package radua.servers.server.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

import radua.servers.server.generics.APacketProviderHandler;

public class SendSessionPacketProviderHandler extends SessionFactoryPacketProviderHandler
{
	
	public ISessionKey createSessionKey(byte[] data, SocketAddress remoteAddr) { return null; }
	public ISession createSession(byte[] data, SocketAddress remoteAddr) { return null; }
	public ISession createSession(ISessionKey key, byte[] data, SocketAddress remoteAddr) { return null; }
	public boolean shouldCreate(byte[] data, SocketAddress remoteAddr) { return false; }

	
	public ISession send(byte[] data, SocketAddress remoteAddr, boolean hasReplay) throws IOException
	{
		if (!hasReplay) {
			transmitPacket(data, remoteAddr);
			return null;
		}
		
		ISession session = createSession(data, remoteAddr);
		ISession oldSession = sendMap.putIfAbsent(session.getKey(), session);
		if (oldSession != null) session = oldSession;
		
		getProvider().transmitPacket(data, remoteAddr);
		return session;
	}
	
	
	@Override
	public void transmitPacket(byte[] data, SocketAddress remoteAddr) throws IOException
	{
		getProvider().transmitPacket(data, remoteAddr);
	}
}
