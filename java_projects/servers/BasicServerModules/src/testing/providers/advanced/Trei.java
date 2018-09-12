package testing.providers.advanced;

import java.io.IOException;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.IPacketHandler;
import radua.servers.packetProcs.basics.ARunPacketProviderHandler;
import radua.utils.errors.generic.ImmutableVariable;

public class Trei extends ARunPacketProviderHandler
{
	public Trei() {}
	public Trei(IPacketHandler nHandler) throws ImmutableVariable
	{
		setHandler(nHandler);
		nHandler.setProvider(this);
	}
	
	protected void internalStart() { System.out.println("Trei - start!"); }
	protected void internalStop() { System.out.println("Trei - stop!"); }
	protected void internalStopWait() { System.out.println("Trei - stop wait!"); }
	
	public void handlePacket(IPacket packet)
	{
		System.out.println("Trei - Handle Packet!");
		getHandler().handlePacket(packet);
	}
	public void transmitPacket(IPacket packet) throws IOException
	{
		System.out.println("Trei - Send packet!");
		getProvider().transmitPacket(packet);
	}
}
