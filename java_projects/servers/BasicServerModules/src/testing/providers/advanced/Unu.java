package testing.providers.advanced;

import java.io.IOException;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.linking.ARunPacketOuter_S;

public class Unu extends ARunPacketOuter_S
{
	protected void internalStart() { System.out.println("Unu - start!"); }
	protected void internalStop() { System.out.println("Unu - stop!"); }
	protected void internalStopWait() { System.out.println("Unu - stop wait!"); }
	
	public void packetReceived()
	{
		System.out.println("Unu - Packet Received!");
		getInner().handlePacket(null);
	}

	public boolean transmitPacket(IPacket packet) throws IOException
	{
		System.out.println("Unu - Send Packet!");
		return true;
	}
}
