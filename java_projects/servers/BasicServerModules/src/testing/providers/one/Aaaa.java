package testing.providers.one;

import java.io.IOException;
import java.net.SocketAddress;

import radua.servers.server.generics.old.ARunPacketProvider;

public class Aaaa extends ARunPacketProvider
{
	protected void internalStart() { System.out.println("Aaaa - start!"); }
	protected void internalStop() { System.out.println("Aaaa - stop!"); }
	protected void internalStopWait() { System.out.println("Aaaa - stop wait!"); }

	public void packetReceived()
	{
		System.out.println("Aaaa - Packet Received!");
		handler.handlePacket(null);
	}

	public void transmitPacket(byte[] data, SocketAddress remoteAddr) throws IOException
	{
		System.out.println("Aaaa - Send Packet!");
	}
}
