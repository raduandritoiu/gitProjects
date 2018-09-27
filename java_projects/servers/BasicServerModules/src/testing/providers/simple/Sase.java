package testing.providers.simple;

import java.io.IOException;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.linking.ARunPacketMiddle_SS;

public class Sase extends ARunPacketMiddle_SS
{
	protected void internalStart() { System.out.println("Sase - start!"); }
	protected void internalStop() { System.out.println("Sase - stop!"); }
	protected void internalStopWait() { System.out.println("Sase - stop wait!"); }

	
	public boolean handlePacket(IPacket packet)
	{
		System.out.println("Sase - Handle Packet!");
		if (getInner() != null)
			getInner().handlePacket(packet);
		return true;
	}
	public boolean transmitPacket(IPacket packet) throws IOException
	{
		System.out.println("Sase - Send packet!");
		if (getOuter() != null)
			return getOuter().transmitPacket(packet);
		return false;
	}
}
