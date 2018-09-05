package testing.providers.advanced;

import java.io.IOException;
import java.net.SocketAddress;

import radua.servers.server.generics.ARunPacketProvider;

public class Alpha extends ARunPacketProvider
{
	protected void internalStart() { System.out.println("Aaaa - start!"); }
	protected void internalStop() { System.out.println("Aaaa - stop!"); }
	protected void internalStopWait() { System.out.println("Aaaa - stop wait!"); }

	public void packetReceived()
	{
		System.out.println("Aaaa - Packet Received!");
		getHandler().handlePacket(null);
	}

	public void transmitPacket(byte[] data, SocketAddress remoteAddr) throws IOException
	{
		System.out.println("Aaaa - Send Packet!");
	}
}
