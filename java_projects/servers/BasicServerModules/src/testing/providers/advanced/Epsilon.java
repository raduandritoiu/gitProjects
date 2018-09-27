package testing.providers.advanced;

import java.io.IOException;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.linking.ARunPacketInner_S;

public class Epsilon extends ARunPacketInner_S
{
	protected void internalStart() { System.out.println("Eeee - start!"); }
	protected void internalStop() { System.out.println("Eeee - stop!"); }
	protected void internalStopWait() { System.out.println("Eeee - stop wait!"); }

	public boolean handlePacket(IPacket packet) 
	{
		System.out.println("Eeee - Handle Packet!");
		System.out.println("Eeee - prepare a Reply!");
		try { getOuter().transmitPacket(null); }
		catch (IOException ex) {}
		return true;
	}
	
	public void tick() throws IOException
	{
		System.out.println("Eeee - Send packet DOWN!");
		getOuter().transmitPacket(null);
	}
}
