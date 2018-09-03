package radua.servers.server.udp;

import java.net.DatagramPacket;

import radua.servers.server.generics.APacketHandler;

public class SMPacketHandler extends APacketHandler
{
	private int state = 0;
	
	@Override
	public void handlePacket(DatagramPacket packet) 
	{
		String req = new String(packet.getData(), 0, packet.getLength());
		System.out.println("Received packet <" + req + ">:" + packet.getLength() + ":" + packet.getData().length + 
				" from <" + packet.getSocketAddress() + ">");
		String resp = getStateString();
		System.out.println("Send back: <" + resp + "> to <" + packet.getSocketAddress() + ">");
		try { getProvider().transmitPacket(resp.getBytes(), packet.getSocketAddress()); }
		catch (Exception ex) { System.out.println("FUCK: " + ex); }
	}
	
	
	private String getStateString()
	{
		String stateStr = "";
		switch (state) {
		case 0:
			stateStr = "Buna eu sunt Two.";
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
