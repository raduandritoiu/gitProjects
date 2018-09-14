package radua.servers.packetProcs;

public interface IPacketHandlerSingle extends IPacketHandler
{
	void setProvider(IPacketProvider provider);
	IPacketProvider getProvider();
}
