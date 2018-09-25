package radua.servers.packetProcs;

public interface IPacketHandler_proc extends IPacketProcessor
{
	/** This function should not be used outside of Provider / Handler context. */
	void handlePacket(IPacket packet);
}
