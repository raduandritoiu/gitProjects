package testing.providers.one;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;

import radua.servers.server.generics.ARunPacketProviderHandler;
import radua.servers.server.generics.IPacketProvider;
import radua.utils.errors.generic.ImmutableVariable;

public class Dddd extends ARunPacketProviderHandler
{
	public Dddd(IPacketProvider nProvider) throws ImmutableVariable
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
	public void sendPacket(byte[] data, SocketAddress remoteAddr) throws IOException
	{
		System.out.println("Dddd - Send packet!");
		provider.sendPacket(data, remoteAddr);
	}
}
