package testing.providers.advanced;

import java.io.IOException;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.linking.ARunPacketInner_S;

public class Cinci extends ARunPacketInner_S
{
	protected void internalStart() { System.out.println("Cinci - start!"); }
	protected void internalStop() { System.out.println("Cinci - stop!"); }
	protected void internalStopWait() { System.out.println("Cinci - stop wait!"); }
	
	public boolean handlePacket(IPacket packet) 
	{
		System.out.println("Cinci - Handle Packet!");
		System.out.println("Cinci - prepare a Reply!");
		try { getOuter().transmitPacket(null); }
		catch (IOException ex) {}
		return true;
	}
	
	public void tick() throws IOException
	{
		System.out.println("Cinci - Send packet DOWN!");
		getOuter().transmitPacket(null);
	}
}
