package testing.providers.simple;

import java.io.IOException;
import java.net.DatagramPacket;

import radua.servers.server.generics.ARunPacketHandler;

public class Sapte extends ARunPacketHandler
{
	protected void internalStart() { System.out.println("Sapte - start!"); }
	protected void internalStop() { System.out.println("Sapte - stop!"); }
	protected void internalStopWait() { System.out.println("Sapte - stop wait!"); }
	
	
	public void handlePacket(DatagramPacket packet) 
	{
		System.out.println("Sapte - Handle Packet!");
		System.out.println("Sapte - prepare a Reply!");
		try { getProvider().transmitPacket(null, null); }
		catch (IOException ex) {}
	}
	public void tick() throws IOException
	{
		System.out.println("Sapte - Send packet DOWN!");
		if (getProvider() != null)
			getProvider().transmitPacket(null, null);
	}
}