package testing.providers.ver1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;

import radua.servers.server.generics.ver1.ARunPacketProviderHandler;
import radua.servers.server.generics.ver1.IPacketHandler;
import radua.utils.errors.generic.ImmutableVariable;

public class Patru extends ARunPacketProviderHandler
{
	public Patru(IPacketHandler nHandler) throws ImmutableVariable
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
	public void transmitPacket(byte[] data, SocketAddress remoteAddr) throws IOException
	{
		System.out.println("Patru - Send packet!");
		getProvider().transmitPacket(data, remoteAddr);
	}
}
