package radua.servers.packetProcs.helpers;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.linking.APacketInner_S;
import radua.utils.logs.Log;

public class EchoHandler extends APacketInner_S
{
	public boolean handlePacket(IPacket packet) 
	{
		try { getOuter().transmitPacket(packet); }
		catch (Exception ex) { Log._out("FUCK: " + ex); }
		return true;
	}
}
