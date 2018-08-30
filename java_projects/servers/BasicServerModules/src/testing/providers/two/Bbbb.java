
package testing.providers.two;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;

import radua.servers.server.general.A_RunPacketProviderHandler;
import radua.servers.server.general.I_PacketProvider;
import radua.utils.errors.generic.ImmutableVariable;

public class Bbbb extends A_RunPacketProviderHandler
{
	public Bbbb(I_PacketProvider nProvider) throws ImmutableVariable
	{
		setProvider(nProvider);
		nProvider.setHandler(this);
	}
	
	protected void internalStart() { System.out.println("Bbbb - start!"); }
	protected void internalStop() { System.out.println("Bbbb - stop!"); }
	protected void internalStopWait() { System.out.println("Bbbb - stop wait!"); }

	public void handlePacket(DatagramPacket packet)
	{
		System.out.println("Bbbb - Handle Packet!");
		getHandler().handlePacket(packet);
	}
	public void sendPacket(byte[] data, SocketAddress remoteAddr) throws IOException
	{
		System.out.println("Bbbb - Send packet!");
		getProvider().sendPacket(data, remoteAddr);
	}
}
