package testing.providers.advanced;

import java.io.IOException;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.linking.ARunPacketMiddle_SS;

public class Delta extends ARunPacketMiddle_SS
{
	protected void internalStart() { System.out.println("Dddd - start!"); }
	protected void internalStop() { System.out.println("Dddd - stop!"); }
	protected void internalStopWait() { System.out.println("Dddd - stop wait!"); }

	public boolean handlePacket(IPacket packet)
	{
		System.out.println("Dddd - Handle Packet!");
		getInner().handlePacket(packet);
		return true;
	}
	public boolean transmitPacket(IPacket packet) throws IOException
	{
		System.out.println("Dddd - Send packet!");
		return getOuter().transmitPacket(packet);
	}
}
