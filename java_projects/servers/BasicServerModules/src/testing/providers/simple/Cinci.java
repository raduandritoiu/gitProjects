package testing.providers.simple;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;

import radua.servers.server.generics.ARunPacketProviderHandler;

public class Cinci extends ARunPacketProviderHandler
{
	protected void internalStart() { System.out.println("Cinci - start!"); }
	protected void internalStop() { System.out.println("Cinci - stop!"); }
	protected void internalStopWait() { System.out.println("Cinci - stop wait!"); }

	
	public void handlePacket(DatagramPacket packet)
	{
		System.out.println("Cinci - Handle Packet!");
		if (getHandler() != null)
			getHandler().handlePacket(packet);
	}
	public void transmitPacket(byte[] data, SocketAddress remoteAddr) throws IOException
	{
		System.out.println("Cinci - Send packet!");
		if (getProvider() != null)
			getProvider().transmitPacket(data, remoteAddr);
	}
}
