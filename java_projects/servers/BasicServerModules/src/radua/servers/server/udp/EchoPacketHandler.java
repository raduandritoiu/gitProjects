package radua.servers.server.udp;

import java.net.DatagramPacket;

import radua.servers.server.generics.APacketHandler;
import radua.utils.logs.Log;

public class EchoPacketHandler extends APacketHandler
{
	public void handlePacket(DatagramPacket packet) 
	{
		try { getProvider().transmitPacket(packet.getData(), packet.getSocketAddress()); }
		catch (Exception ex) { Log._out("FUCK: " + ex); }
	}
}
