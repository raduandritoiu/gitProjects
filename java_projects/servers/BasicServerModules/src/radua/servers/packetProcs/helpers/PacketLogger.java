package radua.servers.packetProcs.helpers;

import java.io.IOException;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.linking.APacketMiddle_SS;
import radua.utils.logs.Log;

public class PacketLogger extends APacketMiddle_SS
{
	private Log log;
	
	public PacketLogger() { this(new Log()); }
	public PacketLogger(Log nLog) { log = nLog; }
	
	public boolean transmitPacket(IPacket packet) throws IOException 
	{
		String resp = new String(packet.data());
		log.out("Send packet back to <" + packet.remoteAddr() + "> : <" + resp + ">" + packet.dataLen());
		if (getOuter() != null)
			return getOuter().transmitPacket(packet);
		return false;
	}

	public boolean handlePacket(IPacket packet) 
	{
		String req = new String(packet.data(), 0, packet.dataLen());
		log.out("Received packet from <" + packet.remoteAddr() + "> : <" + req + ">" + packet.dataLen() + "/" + packet.data().length);
		if (getInner() != null)
			return getInner().handlePacket(packet);
		return false;
	}
}
