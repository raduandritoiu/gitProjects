package radua.servers.server.udp;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.linking.APacketHandlerProvider;
import radua.utils.logs.Log;

public class HostSessionTest extends APacketHandlerProvider
{
	private final ConcurrentHashMap<SocketAddress, InternalHostSession> sessionsMap;
	
	
	public HostSessionTest()
	{
		sessionsMap = new ConcurrentHashMap<>();
	}
	
	
	public void transmitPacket(IPacket packet) throws IOException
	{
		getProvider().transmitPacket(packet);
	}

	public void handlePacket(IPacket packet)
	{
		SocketAddress hostAddress = packet.remoteAddr();
		InternalHostSession newSession = new InternalHostSession(hostAddress);
		InternalHostSession session = sessionsMap.put(hostAddress, newSession);
		if (session == null) {
			Log._out("HostSession: new session from host <" + hostAddress + ">");
			session = newSession;
		}
		
		session.f();
		
		getHandler().handlePacket(packet);
	}
	
	
	private class InternalHostSession
	{
		private final SocketAddress hostAddress;
		
		public InternalHostSession(SocketAddress nHostAddress)
		{
			hostAddress = nHostAddress;
		}
		
		public void f()
		{
			Log._out("HostSession f() " + hostAddress);
		}
	}
}
