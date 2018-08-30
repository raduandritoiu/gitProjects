package testing.providers.two;

import java.io.IOException;
import java.net.SocketAddress;

import radua.servers.server.general.A_RunPacketProvider;

public class Aaaa extends A_RunPacketProvider
{
	protected void internalStart() { System.out.println("Aaaa - start!"); }
	protected void internalStop() { System.out.println("Aaaa - stop!"); }
	protected void internalStopWait() { System.out.println("Aaaa - stop wait!"); }

	public void packetReceived()
	{
		System.out.println("Aaaa - Packet Received!");
		getHandler().handlePacket(null);
	}

	public void sendPacket(byte[] data, SocketAddress remoteAddr) throws IOException
	{
		System.out.println("Aaaa - Send Packet!");
	}
}
