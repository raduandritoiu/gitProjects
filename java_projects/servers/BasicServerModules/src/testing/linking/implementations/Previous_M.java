package testing.linking.implementations;

import java.io.IOException;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.linking.ARunPacketOuter_M;
import radua.utils.logs.Logger;


public class Previous_M extends ARunPacketOuter_M
{
	private final String mName;
	
	public Previous_M() { mName = this.getClass().getSimpleName(); }
	public Previous_M(String name) { mName = name; }
	
	public String name() { return mName; }
	
	
	public boolean transmitPacket(IPacket packet) throws IOException 
	{
		Logger.out(mName + " transmits the packet");
		return true;
	}
}
