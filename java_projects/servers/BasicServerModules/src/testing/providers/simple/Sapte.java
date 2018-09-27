package testing.providers.simple;

import java.io.IOException;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.linking.ARunPacketInner_S;

public class Sapte extends ARunPacketInner_S
{
	protected void internalStart() { System.out.println("Sapte - start!"); }
	protected void internalStop() { System.out.println("Sapte - stop!"); }
	protected void internalStopWait() { System.out.println("Sapte - stop wait!"); }
	
	
	public boolean handlePacket(IPacket packet) 
	{
		System.out.println("Sapte - Handle Packet!");
		System.out.println("Sapte - prepare a Reply!");
		try { getOuter().transmitPacket(null); }
		catch (IOException ex) {}
		return true;
	}
	public void tick() throws IOException
	{
		System.out.println("Sapte - Send packet DOWN!");
		if (getOuter() != null)
			getOuter().transmitPacket(null);
	}
}
