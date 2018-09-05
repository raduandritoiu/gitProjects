package testing.providers.ver1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;

import radua.servers.server.generics.ver1.ARunPacketProviderHandler;
import radua.servers.server.generics.ver1.IPacketProvider;
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

	public void handlePacket(DatagramPacket packet)
	{
		System.out.println("Bbbb - Handle Packet!");
		getHandler().handlePacket(packet);
	}
	public void transmitPacket(byte[] data, SocketAddress remoteAddr) throws IOException
	{
		System.out.println("Bbbb - Send packet!");
		getProvider().transmitPacket(data, remoteAddr);
	}
}
