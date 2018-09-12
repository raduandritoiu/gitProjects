package testing.providers.advanced;

import java.io.IOException;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.IPacketHandler;
import radua.servers.packetProcs.basics.ARunPacketProviderHandler;
import radua.utils.errors.generic.ImmutableVariable;

public class Doi extends ARunPacketProviderHandler
{
	public Doi() {}
	public Doi(IPacketHandler nHandler) throws ImmutableVariable
	{
		setHandler(nHandler);
		nHandler.setProvider(this);
	}
	
	protected void internalStart() { System.out.println("Doi - start!"); }
	protected void internalStop() { System.out.println("Doi - stop!"); }
	protected void internalStopWait() { System.out.println("Doi - stop wait!"); }
	
	public void handlePacket(IPacket packet)
	{
		System.out.println("Doi - Handle Packet!");
		getHandler().handlePacket(packet);
	}
	public void transmitPacket(IPacket packet) throws IOException
	{
		System.out.println("Doi - Send packet!");
		getProvider().transmitPacket(packet);
	}
}
