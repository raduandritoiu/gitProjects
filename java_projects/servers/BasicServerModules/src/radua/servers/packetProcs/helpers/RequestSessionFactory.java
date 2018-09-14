package radua.servers.packetProcs.helpers;

import java.io.IOException;
import java.net.SocketAddress;

import radua.servers.packetProcs.Packet;
import radua.servers.packetProcs.IPacket;
import radua.servers.session.ISession;
import radua.servers.session.ISessionKey;
import radua.servers.session.PacketSessionFactory;
import radua.utils.errors.generic.UniqueKeyValue;

public class RequestSessionFactory extends PacketSessionFactory
{
	public RequestSession sendRequest(byte[] data, SocketAddress remoteAddr, boolean hasReplay) throws IOException, UniqueKeyValue
	{
		Packet packet = Packet.Out(data, data.length, remoteAddr);
		return sendRequest(packet, hasReplay);
	}
	
	public RequestSession sendRequest(IPacket packet, boolean hasReplay) throws IOException, UniqueKeyValue
	{
		if (!hasReplay) {
			transmitPacket(packet);
			return null;
		}
		RequestSession session = (RequestSession) addUniqueSession(packet);
		getProvider().transmitPacket(packet);
		return session;
	}
	
	
	@Override
	public boolean shouldCreate(IPacket packet) 
	{
		return false;
	}
	@Override
	public ISessionKey createSessionKey(IPacket packet)
	{ 
		return new RequestSessionKey(packet.remoteAddr());
	}
	@Override
	public ISession createSession(IPacket packet)
	{
		return createSession(createSessionKey(packet), packet);
	}
	@Override
	public ISession createSession(ISessionKey sessionKey, IPacket packet) 
	{
		return new RequestSession((RequestSessionKey) sessionKey);
	}
	
	
//=====================================================================================
//=====================================================================================
	
	private static class RequestSessionKey implements ISessionKey
	{
		private final int hashCode;
		public RequestSessionKey(SocketAddress remoteAddr)
		{
			hashCode = remoteAddr.hashCode();
		}
		
		@Override
		public int hashCode() { return hashCode; }
	}
	
	
	public static class RequestSession implements ISession
	{
		private final RequestSessionKey key;
		private IPacket response;
		
		private RequestSession(RequestSessionKey nKey)
		{
			key = nKey;
		}
		
		@Override
		public ISessionKey key() { return key; }
		
		@Override
		public boolean handlePacket(IPacket packet)
		{
			response = packet;
			return true;
		}
		
		public IPacket getResponse()
		{
			if (response != null)
				return response;
			synchronized (this) {
				while (response == null) {
					try { wait(); }
					catch (Exception e) { /* */ }
				}
			}
			return response;
		}
		
		public IPacket getResponse(long timeout)
		{
			if (response != null)
				return response;
			synchronized (this) {
				while (response == null) {
					try { 
						wait(timeout); 
						break;
					}
					catch (Exception e) { /* */ }
				}
			}
			return response;
		}
	}
}
