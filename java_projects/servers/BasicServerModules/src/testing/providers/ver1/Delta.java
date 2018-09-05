package testing.providers.ver1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;

import radua.servers.server.generics.ver1.ARunPacketProviderHandler;
import radua.servers.server.generics.ver1.IPacketProvider;
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
