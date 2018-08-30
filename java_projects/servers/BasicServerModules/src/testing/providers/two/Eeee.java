package testing.providers.two;

import java.io.IOException;
import java.net.DatagramPacket;

import radua.servers.server.general.A_RunPacketHandler;
import radua.servers.server.general.I_PacketProvider;
import radua.utils.errors.generic.ImmutableVariable;

public class Eeee extends A_RunPacketHandler
{
	public Eeee(I_PacketProvider nProvider) throws ImmutableVariable
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
		try { getProvider().sendPacket(null, null); }
		catch (IOException ex) {}
	}
	
	public void tick() throws IOException
	{
		System.out.println("Eeee - Send packet DOWN!");
		getProvider().sendPacket(null, null);
	}
}
