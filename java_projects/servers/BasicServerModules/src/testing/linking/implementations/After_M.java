package testing.linking.implementations;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.linking.ARunPacketInner_M;
import radua.utils.logs.Logger;


public class After_M extends ARunPacketInner_M
{
	private final String mName;
	
	public After_M() { mName = this.getClass().getSimpleName(); }
	public After_M(String name) { mName = name; }
	
	public String name() { return mName; }
	
	
	public boolean handlePacket(IPacket packet) 
	{
		Logger.out(mName + " handles the packet");
		return true;
	}
}
