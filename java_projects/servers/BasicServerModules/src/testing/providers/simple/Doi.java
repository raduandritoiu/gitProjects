package testing.providers.simple;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;

import radua.servers.server.generics.ARunPacketProviderHandler;

public class Doi extends ARunPacketProviderHandler
{
	protected void internalStart() { System.out.println("Doi - start!"); }
	protected void internalStop() { System.out.println("Doi - stop!"); }
	protected void internalStopWait() { System.out.println("Doi - stop wait!"); }

	
	public void handlePacket(DatagramPacket packet)
	{
		System.out.println("Doi - Handle Packet!");
		if (getHandler() != null)
			getHandler().handlePacket(packet);
	}
	public void transmitPacket(byte[] data, SocketAddress remoteAddr) throws IOException
	{
		System.out.println("Doi - Send packet!");
		if (getProvider() != null)
			getProvider().transmitPacket(data, remoteAddr);
	}
}
