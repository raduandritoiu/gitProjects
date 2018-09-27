package testing.providers.advanced;

import java.io.IOException;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.linking.ARunPacketMiddle_SS;

public class Patru extends ARunPacketMiddle_SS
{
	protected void internalStart() { System.out.println("Patru - start!"); }
	protected void internalStop() { System.out.println("Patru - stop!"); }
	protected void internalStopWait() { System.out.println("Patru - stop wait!"); }
	
	public boolean handlePacket(IPacket packet)
	{
		System.out.println("Patru - Handle Packet!");
		getInner().handlePacket(packet);
		return true;
	}
	public boolean transmitPacket(IPacket packet) throws IOException
	{
		System.out.println("Patru - Send packet!");
		return getOuter().transmitPacket(packet);
	}
}
