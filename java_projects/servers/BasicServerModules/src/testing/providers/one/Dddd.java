package testing.providers.one;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;

import radua.servers.server.generics.old.ARunPacketProviderHandler_old;
import radua.servers.server.generics.old.IPacketProvider_old;
import radua.utils.errors.generic.ImmutableVariable;

public class Dddd extends ARunPacketProviderHandler_old
{
	public Dddd(IPacketProvider_old nProvider) throws ImmutableVariable
	{
		setProvider(nProvider);
		nProvider.setHandler(this);
	}

	protected void internalStart() { System.out.println("Dddd - start!"); }
	protected void internalStop() { System.out.println("Dddd - stop!"); }
	protected void internalStopWait() { System.out.println("Dddd - stop wait!"); }

	public void handlePacket(DatagramPacket packet)
	{
		System.out.println("Dddd - Handle Packet!");
		handler.handlePacket(packet);
	}
	public void transmitPacket(byte[] data, SocketAddress remoteAddr) throws IOException
	{
		System.out.println("Dddd - Send packet!");
		provider.transmitPacket(data, remoteAddr);
	}
}
