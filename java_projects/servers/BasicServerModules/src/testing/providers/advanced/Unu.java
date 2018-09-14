package testing.providers.advanced;

import java.io.IOException;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.linking.ARunPacketProvider;

public class Unu extends ARunPacketProvider
{
	protected void internalStart() { System.out.println("Unu - start!"); }
	protected void internalStop() { System.out.println("Unu - stop!"); }
	protected void internalStopWait() { System.out.println("Unu - stop wait!"); }
	
	public void packetReceived()
	{
		System.out.println("Unu - Packet Received!");
		getHandler().handlePacket(null);
	}

	public void transmitPacket(IPacket packet) throws IOException
	{
		System.out.println("Unu - Send Packet!");
	}
}
