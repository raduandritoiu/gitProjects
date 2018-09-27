package radua.servers.packetProcs;

import java.io.IOException;

public interface IPacketProvider extends IPacketProcessor
{
	/** This function should not be used outside of Provider / Handler context. */
	boolean transmitPacket(IPacket packet) throws IOException;
}
