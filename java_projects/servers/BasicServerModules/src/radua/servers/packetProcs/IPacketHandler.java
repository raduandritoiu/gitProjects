package radua.servers.packetProcs;

public interface IPacketHandler extends IPacketProcess
{
//	void setProvider(IPacketProvider provider);
//	IPacketProvider getProvider();
	
	/** This function should not be used outside of Provider / Handler context. */
	void handlePacket(IPacket packet);
}
