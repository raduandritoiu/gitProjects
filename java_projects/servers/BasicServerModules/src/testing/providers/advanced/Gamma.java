package testing.providers.advanced;

import java.io.IOException;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.IPacketProvider;
import radua.servers.packetProcs.basics.ARunPacketProviderHandler;
import radua.utils.errors.generic.ImmutableVariable;

public class Gamma extends ARunPacketProviderHandler
{
	public Gamma(IPacketProvider nProvider) throws ImmutableVariable
	{
		setProvider(nProvider);
		nProvider.setHandler(this);
	}

	protected void internalStart() { System.out.println("Cccc - start!"); }
	protected void internalStop() { System.out.println("Cccc - stop!"); }
	protected void internalStopWait() { System.out.println("Cccc - stop wait!"); }

	public void handlePacket(IPacket packet)
	{
		System.out.println("Cccc - Handle Packet!");
		getHandler().handlePacket(packet);
	}
	public void transmitPacket(IPacket packet) throws IOException
	{
		System.out.println("Cccc - Send packet!");
		getProvider().transmitPacket(packet);
	}
}
