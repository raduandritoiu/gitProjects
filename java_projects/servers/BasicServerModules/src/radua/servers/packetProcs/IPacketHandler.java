package radua.servers.packetProcs;

public interface IPacketHandler extends IPacketProcessor
{
	/** This function should not be used outside of Provider / Handler context. */
	boolean handlePacket(IPacket packet);
}
