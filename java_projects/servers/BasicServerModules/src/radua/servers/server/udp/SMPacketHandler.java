package radua.servers.server.udp;

import java.net.DatagramPacket;

import radua.servers.server.generics.APacketHandler;
import radua.utils.logs.Log;

public class SMPacketHandler extends APacketHandler
{
	private int state = 0;
	
	public void handlePacket(DatagramPacket packet) 
	{
		String resp = getStateString();
		try { getProvider().transmitPacket(resp.getBytes(), packet.getSocketAddress()); }
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
