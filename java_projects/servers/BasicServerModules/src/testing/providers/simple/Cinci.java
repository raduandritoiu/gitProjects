package testing.providers.simple;

import java.io.IOException;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.linking.ARunPacketHandlerProvider;

public class Cinci extends ARunPacketHandlerProvider
{
	protected void internalStart() { System.out.println("Cinci - start!"); }
	protected void internalStop() { System.out.println("Cinci - stop!"); }
	protected void internalStopWait() { System.out.println("Cinci - stop wait!"); }

	
	public void handlePacket(IPacket packet)
	{
		System.out.println("Cinci - Handle Packet!");
		if (getHandler() != null)
			getHandler().handlePacket(packet);
	}
	public void transmitPacket(IPacket packet) throws IOException
	{
		System.out.println("Cinci - Send packet!");
		if (getProvider() != null)
			getProvider().transmitPacket(packet);
	}
}
