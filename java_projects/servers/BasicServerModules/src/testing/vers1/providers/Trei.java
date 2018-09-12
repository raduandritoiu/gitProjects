package testing.vers1.providers;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;

import radua.servers.vers1.server.generics.ARunPacketProviderHandler;
import radua.servers.vers1.server.generics.IPacketHandler;
import radua.utils.errors.generic.ImmutableVariable;

public class Trei extends ARunPacketProviderHandler
{
	public Trei(IPacketHandler nHandler) throws ImmutableVariable
	{
		setHandler(nHandler);
		nHandler.setProvider(this);
	}
	
	protected void internalStart() { System.out.println("Trei - start!"); }
	protected void internalStop() { System.out.println("Trei - stop!"); }
	protected void internalStopWait() { System.out.println("Trei - stop wait!"); }
	
	public void handlePacket(DatagramPacket packet)
	{
		System.out.println("Trei - Handle Packet!");
		getHandler().handlePacket(packet);
	}
	public void transmitPacket(byte[] data, SocketAddress remoteAddr) throws IOException
	{
		System.out.println("Trei - Send packet!");
		getProvider().transmitPacket(data, remoteAddr);
	}
}
