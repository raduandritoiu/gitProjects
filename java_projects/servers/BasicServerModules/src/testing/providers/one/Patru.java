package testing.providers.one;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;

import radua.servers.server.generics.old.ARunPacketProviderHandler_old;
import radua.servers.server.generics.old.IPacketHandler_old;
import radua.utils.errors.generic.ImmutableVariable;

public class Patru extends ARunPacketProviderHandler_old
{
	public Patru(IPacketHandler_old nHandler) throws ImmutableVariable
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
		handler.handlePacket(packet);
	}
	public void transmitPacket(byte[] data, SocketAddress remoteAddr) throws IOException
	{
		System.out.println("Patru - Send packet!");
		provider.transmitPacket(data, remoteAddr);
	}
}
