package radua.servers.server.udp;

import java.net.DatagramPacket;

import radua.servers.server.generics.APacketHandler;

public class EchoPacketHandler extends APacketHandler
{
	public void handlePacket(DatagramPacket packet) 
	{
		String str = new String(packet.getData(), 0, packet.getLength());
		System.out.println("Received packet <" + str + " : " + packet.getLength() + " : " + packet.getData().length + 
				"> from <" + packet.getSocketAddress() + ">. Send it back!");
		try { getProvider().transmitPacket(packet.getData(), packet.getSocketAddress()); }
		catch (Exception ex) { System.out.println("FUCK: " + ex); }
	}
}
