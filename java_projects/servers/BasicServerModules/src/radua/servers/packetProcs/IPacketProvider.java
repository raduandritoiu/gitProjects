package radua.servers.packetProcs;

import java.io.IOException;

public interface IPacketProvider extends IPacketProcess
{
//	void setHandler(IPacketHandler handler);
//	IPacketHandler getHandler();
	
	/** This function should not be used outside of Provider / Handler context. */
	void transmitPacket(IPacket packet) throws IOException;
}
