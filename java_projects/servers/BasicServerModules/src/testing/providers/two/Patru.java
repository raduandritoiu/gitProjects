package testing.providers.two;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;

import radua.servers.server.general.A_RunPacketProviderHandler;
import radua.servers.server.general.I_PacketHandler;
import radua.utils.errors.generic.ImmutableVariable;

public class Patru extends A_RunPacketProviderHandler
{
	public Patru(I_PacketHandler nHandler) throws ImmutableVariable
	{
		setHandler(nHandler);
		nHandler.setProvider(this);
	}
	
	protected void internalStart() { System.out.println("Patru - start!"); }
	protected void internalStop() { System.out.println("Patru - stop!"); }
	protected void internalStopWait() { System.out.println("Patru - stop wait!"); }
	
	public void handlePacket(DatagramPacket packet)
	{
		System.out.println("Patru - Handle Packet!");
		getHandler().handlePacket(packet);
	}
	public void sendPacket(byte[] data, SocketAddress remoteAddr) throws IOException
	{
		System.out.println("Patru - Send packet!");
		getProvider().sendPacket(data, remoteAddr);
	}
}
