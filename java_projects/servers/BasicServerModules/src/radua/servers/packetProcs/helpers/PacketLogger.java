package radua.servers.packetProcs.helpers;

import java.io.IOException;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.linking.APacketHandlerProvider;
import radua.utils.logs.Log;

public class PacketLogger extends APacketHandlerProvider
{
	private Log log;
	
	public PacketLogger()
	{
		this(new Log());
	}
	
	public PacketLogger(Log nLog)
	{
		log = nLog;
	}
	
	public void transmitPacket(IPacket packet) throws IOException 
	{
		String resp = new String(packet.data());
		log.out("Send packet back to <" + packet.remoteAddr() + "> : <" + resp + ">" + packet.dataLen());
		if (getProvider() != null)
			getProvider().transmitPacket(packet);
	}

	public void handlePacket(IPacket packet) 
	{
		String req = new String(packet.data(), 0, packet.dataLen());
		log.out("Received packet from <" + packet.remoteAddr() + "> : <" + req + ">" + packet.dataLen() + "/" + packet.data().length);
		if (getHandler() != null)
			getHandler().handlePacket(packet);
	}
}
