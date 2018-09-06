package testing.providers.simple;

import java.io.IOException;
import java.net.SocketAddress;

import radua.servers.server.generics.ARunPacketProvider;

public class Unu extends ARunPacketProvider
{
	protected void internalStart() { System.out.println("Unu - start!"); }
	protected void internalStop() { System.out.println("Unu - stop!"); }
	protected void internalStopWait() { System.out.println("Unu - stop wait!"); }
	
	
	public void packetReceived()
	{
		System.out.println("Unu - Packet Received!");
		if (getHandler() != null)
			getHandler().handlePacket(null);
	}
	public void transmitPacket(byte[] data, SocketAddress remoteAddr) throws IOException
	{
		System.out.println("Unu - Send Packet!");
	}
}
