package radua.servers.packetProcs.helpers;

import radua.servers.packetProcs.GenericPacket;
import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.basics.APacketHandler;
import radua.utils.logs.Log;

public class SMPacketHandler extends APacketHandler
{
	private int state = 0;
	
	public void handlePacket(IPacket packet) 
	{
		String resp = getStateString();
		byte[] resp_bytes = resp.getBytes();
		try { getProvider().transmitPacket(new GenericPacket(resp_bytes.length, resp_bytes, packet.remoteAddr())); }
		catch (Exception ex) { Log._out("FUCK: " + ex); }
	}
	
	private String getStateString()
	{
		String stateStr = "";
		switch (state) {
		case 0:
			stateStr = "Buna eu sunt \"The Server\".";
			break;
		case 1:
			stateStr = "Fac bine, multumesc.";
			break;
		case 2:
			stateStr = "Am 12.";
			break;
		}
		state = (state + 1) % 3;
		return stateStr;
	}
}
