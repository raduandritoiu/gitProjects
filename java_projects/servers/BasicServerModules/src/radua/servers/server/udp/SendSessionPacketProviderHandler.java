package radua.servers.server.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

import radua.servers.server.generics.APacketProviderHandler;

public class SendSessionPacketProviderHandler extends APacketProviderHandler 
{
	private final ConcurrentHashMap<ISessionKey, ISession> sendMap;

	
	public SendSessionPacketProviderHandler() 
	{
		sendMap = new ConcurrentHashMap<>();
	}
	
	
	
	protected ISessionKey createSessionKey(byte[] data, SocketAddress remoteAddr) { return null; }
	protected ISession createSession(ISessionKey key, byte[] data, SocketAddress remoteAddr) { return null; }
	
	protected boolean addsSession(byte[] data, SocketAddress remoteAddr) { return true; }
	protected boolean removesSession(byte[] data, SocketAddress remoteAddr) { return true; }
	
	
	public void send(byte[] data, SocketAddress remoteAddr, boolean hasReplay) throws IOException
	{
		if (!hasReplay) {
			transmitPacket(data, remoteAddr);
			return;
		}
		
		ISessionKey sessionKey = createSessionKey(data, remoteAddr);
		ISession oldSession = sendMap.get(sessionKey);
		
	}
	
	
	@Override
	public void handlePacket(DatagramPacket packet) 
	{
		byte[] data = packet.getData();
		SocketAddress remoteAddr = packet.getSocketAddress();
		
		ISessionKey sessionKey = createSessionKey(data, remoteAddr);
		ISession session = null;
		
		// l-am gasit 
		if (!sendMap.isEmpty() && (session = sendMap.get(sessionKey)) != null) { 
			session.poke(data, remoteAddr);
			if (removesSession(data, remoteAddr)) {
				sendMap.remove(sessionKey);
			}
		}
		// nu este acolo
		else if (addsSession(data, remoteAddr)) {
			session = createSession(sessionKey, data, remoteAddr);
			ISession oldSession = sendMap.putIfAbsent(sessionKey, session);
			
			// in the meantime someone added the session
			if (oldSession != null) {
				oldSession.poke(data, remoteAddr);
			}
		}
		
	
		
	}

	@Override
	public void transmitPacket(byte[] data, SocketAddress remoteAddr) throws IOException
	{

	}
}
