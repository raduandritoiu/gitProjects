package testing.vers1.providers;

import java.io.IOException;
import java.net.SocketAddress;

import radua.servers.vers1.server.generics.ARunPacketProvider;
import radua.servers.vers1.server.generics.IPacketHandler;
import radua.utils.errors.generic.ImmutableVariable;

public class Unu extends ARunPacketProvider
{
	public Unu(IPacketHandler nHandler) throws ImmutableVariable
	{
		setHandler(nHandler);
		nHandler.setProvider(this);
	}
	
	protected void internalStart() { System.out.println("Unu - start!"); }
	protected void internalStop() { System.out.println("Unu - stop!"); }
	protected void internalStopWait() { System.out.println("Unu - stop wait!"); }
	
	public void packetReceived()
	{
		System.out.println("Unu - Packet Received!");
		getHandler().handlePacket(null);
	}

	public void transmitPacket(byte[] data, SocketAddress remoteAddr) throws IOException
	{
		System.out.println("Unu - Send Packet!");
	}
}
