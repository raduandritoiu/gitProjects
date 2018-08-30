package testing.providers.one;

import java.io.IOException;
import java.net.DatagramPacket;

import radua.servers.server.generics.ARunPacketHandler;

public class Cinci extends ARunPacketHandler
{
	protected void internalStart() { System.out.println("Cinci - start!"); }
	protected void internalStop() { System.out.println("Cinci - stop!"); }
	protected void internalStopWait() { System.out.println("Cinci - stop wait!"); }
	
	public void handlePacket(DatagramPacket packet) 
	{
		System.out.println("Cinci - Handle Packet!");
		System.out.println("Cinci - prepare a Reply!");
		try { provider.sendPacket(null, null); }
		catch (IOException ex) {}
	}
	
	public void tick() throws IOException
	{
		System.out.println("Cinci - Send packet DOWN!");
		provider.sendPacket(null, null);
	}
}
