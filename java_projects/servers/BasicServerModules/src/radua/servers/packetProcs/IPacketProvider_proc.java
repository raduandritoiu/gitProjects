package radua.servers.packetProcs;

import java.io.IOException;

public interface IPacketProvider_proc extends IPacketProcessor
{
	/** This function should not be used outside of Provider / Handler context. */
	void transmitPacket(IPacket packet) throws IOException;
}
