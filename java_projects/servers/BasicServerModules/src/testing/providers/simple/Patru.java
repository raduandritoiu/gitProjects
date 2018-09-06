package testing.providers.simple;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;

import radua.servers.server.generics.ARunPacketProviderHandler;

public class Patru extends ARunPacketProviderHandler
{
	protected void internalStart() { System.out.println("Patru - start!"); }
	protected void internalStop() { System.out.println("Patru - stop!"); }
	protected void internalStopWait() { System.out.println("Patru - stop wait!"); }

	
	public void handlePacket(DatagramPacket packet)
	{
		System.out.println("Patru - Handle Packet!");
		if (getHandler() != null)
			getHandler().handlePacket(packet);
	}
	public void transmitPacket(byte[] data, SocketAddress remoteAddr) throws IOException
	{
		System.out.println("Patru - Send packet!");
		if (getProvider() != null)
			getProvider().transmitPacket(data, remoteAddr);
	}
}
