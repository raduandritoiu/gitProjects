package testing.providers.simple;

import java.io.IOException;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.basics.ARunPacketProviderHandler;

public class Patru extends ARunPacketProviderHandler
{
	protected void internalStart() { System.out.println("Patru - start!"); }
	protected void internalStop() { System.out.println("Patru - stop!"); }
	protected void internalStopWait() { System.out.println("Patru - stop wait!"); }

	
	public void handlePacket(IPacket packet)
	{
		System.out.println("Patru - Handle Packet!");
		if (getHandler() != null)
			getHandler().handlePacket(packet);
	}
	public void transmitPacket(IPacket packet) throws IOException
	{
		System.out.println("Patru - Send packet!");
		if (getProvider() != null)
			getProvider().transmitPacket(packet);
	}
}
