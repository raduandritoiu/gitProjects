package testing.providers.two;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;

import radua.servers.server.general.A_RunPacketProviderHandler;
import radua.servers.server.general.I_PacketProvider;
import radua.utils.errors.generic.ImmutableVariable;

public class Cccc extends A_RunPacketProviderHandler
{
	public Cccc(I_PacketProvider nProvider) throws ImmutableVariable
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
		getHandler().handlePacket(packet);
	}
	public void sendPacket(byte[] data, SocketAddress remoteAddr) throws IOException
	{
		System.out.println("Cccc - Send packet!");
		getProvider().sendPacket(data, remoteAddr);
	}
}
