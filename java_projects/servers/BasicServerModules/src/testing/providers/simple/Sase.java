package testing.providers.simple;

import java.io.IOException;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.basics.ARunPacketProviderHandler;

public class Sase extends ARunPacketProviderHandler
{
	protected void internalStart() { System.out.println("Sase - start!"); }
	protected void internalStop() { System.out.println("Sase - stop!"); }
	protected void internalStopWait() { System.out.println("Sase - stop wait!"); }

	
	public void handlePacket(IPacket packet)
	{
		System.out.println("Sase - Handle Packet!");
		if (getHandler() != null)
			getHandler().handlePacket(packet);
	}
	public void transmitPacket(IPacket packet) throws IOException
	{
		System.out.println("Sase - Send packet!");
		if (getProvider() != null)
			getProvider().transmitPacket(packet);
	}
}
