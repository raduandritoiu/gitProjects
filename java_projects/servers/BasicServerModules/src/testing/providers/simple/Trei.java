package testing.providers.simple;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;

import radua.servers.server.generics.ARunPacketProviderHandler;

public class Trei extends ARunPacketProviderHandler
{
	protected void internalStart() { System.out.println("Trei - start!"); }
	protected void internalStop() { System.out.println("Trei - stop!"); }
	protected void internalStopWait() { System.out.println("Trei - stop wait!"); }

	
	public void handlePacket(DatagramPacket packet)
	{
		System.out.println("Trei - Handle Packet!");
		if (getHandler() != null)
			getHandler().handlePacket(packet);
	}
	public void transmitPacket(byte[] data, SocketAddress remoteAddr) throws IOException
	{
		System.out.println("Trei - Send packet!");
		if (getProvider() != null)
			getProvider().transmitPacket(data, remoteAddr);
	}
}
