package testing.linking.implementations;

import java.io.IOException;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.linking.ARunPacketMiddle_MM;
import radua.utils.logs.Logger;


public class Middle_MM extends ARunPacketMiddle_MM
{
	private final String mName;
	
	public Middle_MM() { mName = this.getClass().getSimpleName(); }
	public Middle_MM(String name) { mName = name; }
	
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
