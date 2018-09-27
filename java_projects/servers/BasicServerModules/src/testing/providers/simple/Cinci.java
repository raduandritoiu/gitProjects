package testing.providers.simple;

import java.io.IOException;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.linking.ARunPacketMiddle_SS;

public class Cinci extends ARunPacketMiddle_SS
{
	protected void internalStart() { System.out.println("Cinci - start!"); }
	protected void internalStop() { System.out.println("Cinci - stop!"); }
	protected void internalStopWait() { System.out.println("Cinci - stop wait!"); }

	
	public boolean handlePacket(IPacket packet)
	{
		System.out.println("Cinci - Handle Packet!");
		if (getInner() != null)
			getInner().handlePacket(packet);
		return true;
	}
	public boolean transmitPacket(IPacket packet) throws IOException
	{
		System.out.println("Cinci - Send packet!");
		if (getOuter() != null)
			return getOuter().transmitPacket(packet);
		return false;
	}
}
