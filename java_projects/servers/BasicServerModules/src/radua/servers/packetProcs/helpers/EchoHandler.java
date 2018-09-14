package radua.servers.packetProcs.helpers;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.linking.APacketHandler;
import radua.utils.logs.Log;

public class EchoHandler extends APacketHandler
{
	public void handlePacket(IPacket packet) 
	{
		try { getProvider().transmitPacket(packet); }
		catch (Exception ex) { Log._out("FUCK: " + ex); }
	}
}
