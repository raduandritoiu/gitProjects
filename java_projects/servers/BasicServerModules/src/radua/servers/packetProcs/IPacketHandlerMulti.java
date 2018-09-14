package radua.servers.packetProcs;

public interface IPacketHandlerMulti extends IPacketHandler
{
	boolean hasProvider(IPacketProvider nProvider);
	IPacketProvider addProvider(IPacketProvider nProvider, int pos);
	IPacketProvider removeProvider(IPacketProvider nProvider);
	IPacketProvider getProvider(int pos);
	int getProviderPos(IPacketProvider nProvider);
	int getProvidersNum();
}
