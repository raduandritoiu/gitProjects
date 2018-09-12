package testing.providers.advanced;

import java.io.IOException;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.IPacketHandler;
import radua.servers.packetProcs.basics.ARunPacketProviderHandler;
import radua.utils.errors.generic.ImmutableVariable;

public class Patru extends ARunPacketProviderHandler
{
	public Patru() {}
	public Patru(IPacketHandler nHandler) throws ImmutableVariable
	{
		setHandler(nHandler);
		nHandler.setProvider(this);
	}
	
	protected void internalStart() { System.out.println("Patru - start!"); }
	protected void internalStop() { System.out.println("Patru - stop!"); }
	protected void internalStopWait() { System.out.println("Patru - stop wait!"); }
	
	public void handlePacket(IPacket packet)
	{
		System.out.println("Patru - Handle Packet!");
		getHandler().handlePacket(packet);
	}
	public void transmitPacket(IPacket packet) throws IOException
	{
		System.out.println("Patru - Send packet!");
		getProvider().transmitPacket(packet);
	}
}
