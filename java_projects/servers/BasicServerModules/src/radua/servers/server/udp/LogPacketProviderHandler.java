package radua.servers.server.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;

import radua.servers.server.generics.APacketProviderHandler;
import radua.servers.server.generics.IPacketHandler;
import radua.utils.errors.generic.ImmutableVariable;
import radua.utils.logs.Log;

public class LogPacketProviderHandler extends APacketProviderHandler
{
	private Log log;
	
	public LogPacketProviderHandler(IPacketHandler nHandler) throws ImmutableVariable
	{
		this(new Log(), nHandler);
	}
	
	public LogPacketProviderHandler(Log nLog, IPacketHandler nHandler) throws ImmutableVariable
	{
		log = nLog;
		setHandler(nHandler);
		nHandler.setProvider(this);
	}
	
	public void transmitPacket(byte[] data, SocketAddress remoteAddr) throws IOException 
	{
		String resp = new String(data);
		log.out("Send packet back to <" + remoteAddr + "> : <" + resp + ">"+data.length);
		if (getProvider() != null)
			getProvider().transmitPacket(data, remoteAddr);
	}

	public void handlePacket(DatagramPacket packet) 
	{
		String req = new String(packet.getData(), 0, packet.getLength());
		log.out("Received packet from <" + packet.getSocketAddress() + "> : <" + req + ">" + packet.getLength() + "/" + packet.getData().length);
		if (getHandler() != null)
			getHandler().handlePacket(packet);
	}
}
