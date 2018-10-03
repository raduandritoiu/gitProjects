package radua.servers.packetProcs.helpers;

import java.io.IOException;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.linking.ARunPacketMiddle_SM;

public class SerialHandlers extends ARunPacketMiddle_SM
{
	public boolean transmitPacket(IPacket packet) throws IOException
	{
		return getOuter().transmitPacket(packet);
	}
	
	public boolean handlePacket(IPacket packet)
	{
		int handlerNum = getInnersNum();
		for (int i = 0; i< handlerNum; i++) {
			if (getInner(i).handlePacket(packet)) {
				return true;
			}
		}
		return false;
	}
}
