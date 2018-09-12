package testing.vers1.providers;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;

import radua.servers.vers1.server.generics.ARunPacketProviderHandler;
import radua.servers.vers1.server.generics.IPacketProvider;
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

	public void handlePacket(DatagramPacket packet)
	{
		System.out.println("Dddd - Handle Packet!");
		getHandler().handlePacket(packet);
	}
	public void transmitPacket(byte[] data, SocketAddress remoteAddr) throws IOException
	{
		System.out.println("Dddd - Send packet!");
		getProvider().transmitPacket(data, remoteAddr);
	}
}
