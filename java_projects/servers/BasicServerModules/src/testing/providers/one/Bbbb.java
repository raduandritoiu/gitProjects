package testing.providers.one;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;

import radua.servers.server.generics.ARunPacketProviderHandler;
import radua.servers.server.generics.IPacketProvider;
import radua.utils.errors.generic.ImmutableVariable;

public class Bbbb extends ARunPacketProviderHandler
{
	public Bbbb(IPacketProvider nProvider) throws ImmutableVariable
	{
		setProvider(nProvider);
		nProvider.setHandler(this);
	}
	
	protected void internalStart() { System.out.println("Bbbb - start!"); }
	protected void internalStop() { System.out.println("Bbbb - stop!"); }
	protected void internalStopWait() { System.out.println("Bbbb - stop wait!"); }

	public void handlePacket(DatagramPacket packet)
	{
		System.out.println("Bbbb - Handle Packet!");
		handler.handlePacket(packet);
	}
	public void sendPacket(byte[] data, SocketAddress remoteAddr) throws IOException
	{
		System.out.println("Bbbb - Send packet!");
		provider.sendPacket(data, remoteAddr);
	}
}
