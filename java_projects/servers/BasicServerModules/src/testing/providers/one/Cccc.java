package testing.providers.one;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;

import radua.servers.server.generics.ARunPacketProviderHandler;
import radua.servers.server.generics.IPacketProvider;
import radua.utils.errors.generic.ImmutableVariable;

public class Cccc extends ARunPacketProviderHandler
{
	public Cccc(IPacketProvider nProvider) throws ImmutableVariable
	{
		setProvider(nProvider);
		nProvider.setHandler(this);
	}

	protected void internalStart() { System.out.println("Cccc - start!"); }
	protected void internalStop() { System.out.println("Cccc - stop!"); }
	protected void internalStopWait() { System.out.println("Cccc - stop wait!"); }

	public void handlePacket(DatagramPacket packet)
	{
		System.out.println("Cccc - Handle Packet!");
		handler.handlePacket(packet);
	}
	public void sendPacket(byte[] data, SocketAddress remoteAddr) throws IOException
	{
		System.out.println("Cccc - Send packet!");
		provider.sendPacket(data, remoteAddr);
	}
}
