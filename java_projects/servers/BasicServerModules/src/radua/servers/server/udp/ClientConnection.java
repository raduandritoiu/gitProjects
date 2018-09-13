package radua.servers.server.udp;

import java.io.IOException;
import java.net.SocketAddress;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.Packet;
import radua.servers.packetProcs.helpers.RequestSessionFactory;
import radua.servers.packetProcs.helpers.RequestSessionFactory.RequestSession;
import radua.utils.errors.generic.UniqueKeyValue;


public class ClientConnection //implements IUdpClient
{
	private final RequestSessionFactory requestProvider;
	private ThreadLocal<RequestSession> crtRequest;
	private SocketAddress destAddr;
	
	
	public ClientConnection(RequestSessionFactory serviceProvider, SocketAddress destAddr)
	{
		this(serviceProvider);
		this.destAddr = destAddr;
	}
	public ClientConnection(RequestSessionFactory serviceProvider)
	{
		requestProvider = serviceProvider;
	}
	
	
	public void write(String msg) throws UniqueKeyValue, IOException
	{
		write(msg, destAddr);
	}
	public void write(String msg, SocketAddress destAddr) throws UniqueKeyValue, IOException
	{
		byte[] data = msg.getBytes();
		IPacket packet = Packet.Out(data, data.length, destAddr);
		crtRequest.set(requestProvider.sendRequest(packet, true));
	}
	
	
	public String read()
	{
		IPacket response = crtRequest.get().getResponse();
		return new String(response.data());
	}
	public String read(long timeout)
	{
		IPacket response = crtRequest.get().getResponse(timeout);
		return new String(response.data());
	}
}
