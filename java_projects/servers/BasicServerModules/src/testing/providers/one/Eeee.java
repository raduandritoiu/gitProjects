package testing.providers.one;

import java.io.IOException;
import java.net.DatagramPacket;

import radua.servers.server.generics.old.ARunPacketHandler_old;
import radua.servers.server.generics.old.IPacketProvider_old;
import radua.utils.errors.generic.ImmutableVariable;

public class Eeee extends ARunPacketHandler_old
{
	public Eeee(IPacketProvider_old nProvider) throws ImmutableVariable
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
		try { provider.transmitPacket(null, null); }
		catch (IOException ex) {}
	}
	
	public void tick() throws IOException
	{
		System.out.println("Eeee - Send packet DOWN!");
		provider.transmitPacket(null, null);
	}
}
