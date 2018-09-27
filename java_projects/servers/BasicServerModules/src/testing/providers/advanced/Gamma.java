package testing.providers.advanced;

import java.io.IOException;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.linking.ARunPacketMiddle_SS;

public class Gamma extends ARunPacketMiddle_SS
{
	protected void internalStart() { System.out.println("Cccc - start!"); }
	protected void internalStop() { System.out.println("Cccc - stop!"); }
	protected void internalStopWait() { System.out.println("Cccc - stop wait!"); }

	public boolean handlePacket(IPacket packet)
	{
		System.out.println("Cccc - Handle Packet!");
		getInner().handlePacket(packet);
		return true;
	}
	public boolean transmitPacket(IPacket packet) throws IOException
	{
		System.out.println("Cccc - Send packet!");
		return getOuter().transmitPacket(packet);
	}
}
