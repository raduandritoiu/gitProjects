package testing.providers.simple;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;

import radua.servers.server.generics.ARunPacketProviderHandler;

public class Sase extends ARunPacketProviderHandler
{
	protected void internalStart() { System.out.println("Sase - start!"); }
	protected void internalStop() { System.out.println("Sase - stop!"); }
	protected void internalStopWait() { System.out.println("Sase - stop wait!"); }

	
	public void handlePacket(DatagramPacket packet)
	{
		System.out.println("Sase - Handle Packet!");
		if (getHandler() != null)
			getHandler().handlePacket(packet);
	}
	public void transmitPacket(byte[] data, SocketAddress remoteAddr) throws IOException
	{
		System.out.println("Sase - Send packet!");
		if (getProvider() != null)
			getProvider().transmitPacket(data, remoteAddr);
	}
}
