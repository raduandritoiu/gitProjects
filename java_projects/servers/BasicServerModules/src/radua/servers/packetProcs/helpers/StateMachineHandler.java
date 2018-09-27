package radua.servers.packetProcs.helpers;

import java.net.SocketAddress;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.Packet;
import radua.servers.packetProcs.PacketDirection;
import radua.servers.packetProcs.linking.APacketInner_S;
import radua.utils.logs.Log;

public class StateMachineHandler extends APacketInner_S
{
	private int state = 0;
	
	public boolean handlePacket(IPacket packet) 
	{
		String response = getStateString();
		try { getOuter().transmitPacket(new SMPacket(response, packet.remoteAddr())); }
		catch (Exception ex) { Log._out("FUCK: " + ex); }
		return true;
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
	
	
//=======================================================================================
//====== Internal Classes ===============================================================
	
	private static class SMPacket extends Packet
	{
		public SMPacket(String message, SocketAddress remoteAddr)
		{
			this(message.getBytes(), remoteAddr);
		}
		
		public SMPacket(byte[] message, SocketAddress remoteAddr)
		{
			super(message, message.length, remoteAddr, PacketDirection.INCOMING);
		}
	}
}
