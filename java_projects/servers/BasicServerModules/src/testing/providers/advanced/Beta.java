package testing.providers.advanced;

import java.io.IOException;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.IPacketProvider;
import radua.servers.packetProcs.basics.ARunPacketProviderHandler;
import radua.utils.errors.generic.ImmutableVariable;

public class Beta extends ARunPacketProviderHandler
{
	public Beta(IPacketProvider nProvider) throws ImmutableVariable
	{
		setProvider(nProvider);
		nProvider.setHandler(this);
	}
	
	protected void internalStart() { System.out.println("Bbbb - start!"); }
	protected void internalStop() { System.out.println("Bbbb - stop!"); }
	protected void internalStopWait() { System.out.println("Bbbb - stop wait!"); }

	public void handlePacket(IPacket packet)
	{
		System.out.println("Bbbb - Handle Packet!");
		getHandler().handlePacket(packet);
	}
	public void transmitPacket(IPacket packet) throws IOException
	{
		System.out.println("Bbbb - Send packet!");
		getProvider().transmitPacket(packet);
	}
}
