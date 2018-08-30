package testing.providers.two;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;

import radua.servers.server.general.A_RunPacketProviderHandler;
import radua.servers.server.general.I_PacketProvider;
import radua.utils.errors.generic.ImmutableVariable;

public class Dddd extends A_RunPacketProviderHandler
{
	public Dddd(I_PacketProvider nProvider) throws ImmutableVariable
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
	public void sendPacket(byte[] data, SocketAddress remoteAddr) throws IOException
	{
		System.out.println("Dddd - Send packet!");
		getProvider().sendPacket(data, remoteAddr);
	}
}
