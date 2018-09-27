package testing.providers.advanced;

import java.io.IOException;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.linking.ARunPacketOuter_S;

public class Alpha extends ARunPacketOuter_S
{
	protected void internalStart() { System.out.println("Aaaa - start!"); }
	protected void internalStop() { System.out.println("Aaaa - stop!"); }
	protected void internalStopWait() { System.out.println("Aaaa - stop wait!"); }

	public void packetReceived()
	{
		System.out.println("Aaaa - Packet Received!");
		getInner().handlePacket(null);
	}

	public boolean transmitPacket(IPacket packet) throws IOException
	{
		System.out.println("Aaaa - Send Packet!");
		return true;
	}
}
