package testing.providers.simple;

import java.io.IOException;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.linking.ARunPacketHandlerProvider;

public class Doi extends ARunPacketHandlerProvider
{
	protected void internalStart() { System.out.println("Doi - start!"); }
	protected void internalStop() { System.out.println("Doi - stop!"); }
	protected void internalStopWait() { System.out.println("Doi - stop wait!"); }

	
	public void handlePacket(IPacket packet)
	{
		System.out.println("Doi - Handle Packet!");
		if (getHandler() != null)
			getHandler().handlePacket(packet);
	}
	public void transmitPacket(IPacket packet) throws IOException
	{
		System.out.println("Doi - Send packet!");
		if (getProvider() != null)
			getProvider().transmitPacket(packet);
	}
}
