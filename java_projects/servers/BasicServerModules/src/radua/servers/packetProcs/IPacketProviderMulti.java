package radua.servers.packetProcs;

public interface IPacketProviderMulti extends IPacketProvider
{
	boolean hasHandler(IPacketHandler nHandler);
	IPacketHandler addHandler(IPacketHandler nHandler, int pos);
	IPacketHandler removeHandler(IPacketHandler nHandler);
	IPacketHandler getHandler(int pos);
	int getHandlerPos(IPacketHandler nHandler);
	int getHandlersNum();
}
