package testing.linking.implementations;

import java.io.IOException;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.linking.ARunPacketOuter_S;
import radua.utils.logs.Logger;


public class Previous_S extends ARunPacketOuter_S
{
	private final String mName;
	
	public Previous_S() { mName = this.getClass().getSimpleName(); }
	public Previous_S(String name) { mName = name; }
	
	public String name() { return mName; }
	
	
	public boolean transmitPacket(IPacket packet) throws IOException 
	{
		Logger.out(mName + " transmits the packet");
		return true;
	}
}
