package testing.providers.two;

import java.io.IOException;
import java.net.DatagramPacket;

import radua.servers.server.general.A_RunPacketHandler;

public class Cinci extends A_RunPacketHandler
{
	protected void internalStart() { System.out.println("Cinci - start!"); }
	protected void internalStop() { System.out.println("Cinci - stop!"); }
	protected void internalStopWait() { System.out.println("Cinci - stop wait!"); }
	
	public void handlePacket(DatagramPacket packet) 
	{
		System.out.println("Cinci - Handle Packet!");
		System.out.println("Cinci - prepare a Reply!");
		try { getProvider().sendPacket(null, null); }
		catch (IOException ex) {}
	}
	
	public void tick() throws IOException
	{
		System.out.println("Cinci - Send packet DOWN!");
		getProvider().sendPacket(null, null);
	}
}
