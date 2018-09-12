package radua.servers.server.udp;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.basics.APacketProviderHandler;
import radua.utils.logs.Log;

public class HostSessionPacketProviderHandler extends APacketProviderHandler
{
	private final ConcurrentHashMap<SocketAddress, HostSession> sessionsMap;
	
	
	public HostSessionPacketProviderHandler()
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
		HostSession newSession = new HostSession(hostAddress);
		HostSession session = sessionsMap.put(hostAddress, newSession);
		if (session == null) {
			Log._out("HostSession: new session from host <" + hostAddress + ">");
			session = newSession;
		}
		
		session.f();
		
		getHandler().handlePacket(packet);
	}
	
	
	private class HostSession
	{
		private final SocketAddress hostAddress;
		
		public HostSession(SocketAddress nHostAddress)
		{
			hostAddress = nHostAddress;
		}
		
		public void f()
		{
			Log._out("HostSession f() " + hostAddress);
		}
	}
}
