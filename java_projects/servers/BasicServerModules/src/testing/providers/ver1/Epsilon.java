package testing.providers.ver1;

import java.io.IOException;
import java.net.DatagramPacket;

import radua.servers.server.generics.ver1.ARunPacketHandler;
import radua.servers.server.generics.ver1.IPacketProvider;
import radua.utils.errors.generic.ImmutableVariable;

public class Epsilon extends ARunPacketHandler
{
	public Epsilon(IPacketProvider nProvider) throws ImmutableVariable
	{
		setProvider(nProvider);
		nProvider.setHandler(this);
	}

	protected void internalStart() { System.out.println("Eeee - start!"); }
	protected void internalStop() { System.out.println("Eeee - stop!"); }
	protected void internalStopWait() { System.out.println("Eeee - stop wait!"); }

	public void handlePacket(DatagramPacket packet) 
	{
		System.out.println("Eeee - Handle Packet!");
		System.out.println("Eeee - prepare a Reply!");
		try { getProvider().transmitPacket(null, null); }
		catch (IOException ex) {}
	}
	
	public void tick() throws IOException
	{
		System.out.println("Eeee - Send packet DOWN!");
		getProvider().transmitPacket(null, null);
	}
}
