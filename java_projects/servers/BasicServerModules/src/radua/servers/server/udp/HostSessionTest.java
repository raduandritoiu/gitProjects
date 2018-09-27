package radua.servers.server.udp;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.linking.APacketMiddle_SS;
import radua.utils.logs.Log;

public class HostSessionTest extends APacketMiddle_SS
{
	private final ConcurrentHashMap<SocketAddress, InternalHostSession> sessionsMap;
	
	
	public HostSessionTest()
	{
		sessionsMap = new ConcurrentHashMap<>();
	}
	
	
	public boolean transmitPacket(IPacket packet) throws IOException
	{
		return getOuter().transmitPacket(packet);
	}

	public boolean handlePacket(IPacket packet)
	{
		SocketAddress hostAddress = packet.remoteAddr();
		InternalHostSession newSession = new InternalHostSession(hostAddress);
		InternalHostSession session = sessionsMap.put(hostAddress, newSession);
		if (session == null) {
			Log._out("HostSession: new session from host <" + hostAddress + ">");
			session = newSession;
		}
		
		session.f();
		
		return getInner().handlePacket(packet);
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
