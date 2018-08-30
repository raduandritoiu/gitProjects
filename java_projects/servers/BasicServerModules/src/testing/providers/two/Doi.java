package testing.providers.two;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;

import radua.servers.server.general.A_RunPacketProviderHandler;
import radua.servers.server.general.I_PacketHandler;
import radua.utils.errors.generic.ImmutableVariable;

public class Doi extends A_RunPacketProviderHandler
{
	public Doi(I_PacketHandler nHandler) throws ImmutableVariable
	{
		setHandler(nHandler);
		nHandler.setProvider(this);
	}
	
	protected void internalStart() { System.out.println("Doi - start!"); }
	protected void internalStop() { System.out.println("Doi - stop!"); }
	protected void internalStopWait() { System.out.println("Doi - stop wait!"); }
	
	public void handlePacket(DatagramPacket packet)
	{
		System.out.println("Doi - Handle Packet!");
		getHandler().handlePacket(packet);
	}
	public void sendPacket(byte[] data, SocketAddress remoteAddr) throws IOException
	{
		System.out.println("Doi - Send packet!");
		getProvider().sendPacket(data, remoteAddr);
	}
}
