package testing.linking.implementations;

import java.io.IOException;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.linking.ARunPacketMiddle_SS;
import radua.utils.logs.Logger;


public class Middle_SS extends ARunPacketMiddle_SS
{
	private final String mName;
	
	public Middle_SS() { mName = this.getClass().getSimpleName(); }
	public Middle_SS(String name) { mName = name; }
	
	public String name() { return mName; }
	
	
	public boolean handlePacket(IPacket packet) 
	{
		Logger.out(mName + " handles the packet");
		return true;
	}
	public boolean transmitPacket(IPacket packet) throws IOException 
	{
		Logger.out(mName + " transmits the packet");
		return true;
	}
}
