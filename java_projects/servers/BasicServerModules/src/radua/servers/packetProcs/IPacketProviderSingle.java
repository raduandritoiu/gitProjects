package radua.servers.packetProcs;

public interface IPacketProviderSingle extends IPacketProvider
{
	void setHandler(IPacketHandler handler);
	IPacketHandler getHandler();
}
