package testing.linking.implementations;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.linking.ARunPacketInner_S;
import radua.utils.logs.Logger;


public class After_S extends ARunPacketInner_S
{
	private final String mName;
	
	public After_S() { mName = this.getClass().getSimpleName(); }
	public After_S(String name) { mName = name; }
	
	public String name() { return mName; }
	
	
	public boolean handlePacket(IPacket packet) 
	{
		Logger.out(mName + " handles the packet");
		return true;
	}
}
