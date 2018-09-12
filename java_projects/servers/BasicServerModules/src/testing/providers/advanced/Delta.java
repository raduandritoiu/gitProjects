package testing.providers.advanced;

import java.io.IOException;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.IPacketProvider;
import radua.servers.packetProcs.basics.ARunPacketProviderHandler;
import radua.utils.errors.generic.ImmutableVariable;

public class Delta extends ARunPacketProviderHandler
{
	public Delta(IPacketProvider nProvider) throws ImmutableVariable
	{
		setProvider(nProvider);
		nProvider.setHandler(this);
	}

	protected void internalStart() { System.out.println("Dddd - start!"); }
	protected void internalStop() { System.out.println("Dddd - stop!"); }
	protected void internalStopWait() { System.out.println("Dddd - stop wait!"); }

	public void handlePacket(IPacket packet)
	{
		System.out.println("Dddd - Handle Packet!");
		getHandler().handlePacket(packet);
	}
	public void transmitPacket(IPacket packet) throws IOException
	{
		System.out.println("Dddd - Send packet!");
		getProvider().transmitPacket(packet);
	}
}
