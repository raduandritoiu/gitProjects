package testing.providers.advanced;

import java.io.IOException;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.IPacketProvider;
import radua.servers.packetProcs.basics.ARunPacketHandler;
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

	public void handlePacket(IPacket packet) 
	{
		System.out.println("Eeee - Handle Packet!");
		System.out.println("Eeee - prepare a Reply!");
		try { getProvider().transmitPacket(null); }
		catch (IOException ex) {}
	}
	
	public void tick() throws IOException
	{
		System.out.println("Eeee - Send packet DOWN!");
		getProvider().transmitPacket(null);
	}
}
