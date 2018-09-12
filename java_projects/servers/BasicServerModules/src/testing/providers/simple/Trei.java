package testing.providers.simple;

import java.io.IOException;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.basics.ARunPacketProviderHandler;

public class Trei extends ARunPacketProviderHandler
{
	protected void internalStart() { System.out.println("Trei - start!"); }
	protected void internalStop() { System.out.println("Trei - stop!"); }
	protected void internalStopWait() { System.out.println("Trei - stop wait!"); }

	
	public void handlePacket(IPacket packet)
	{
		System.out.println("Trei - Handle Packet!");
		if (getHandler() != null)
			getHandler().handlePacket(packet);
	}
	public void transmitPacket(IPacket packet) throws IOException
	{
		System.out.println("Trei - Send packet!");
		if (getProvider() != null)
			getProvider().transmitPacket(packet);
	}
}
