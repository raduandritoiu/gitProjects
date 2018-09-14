package radua.servers.packetProcs.linking;

import radua.servers.packetProcs.IPacketHandler;
import radua.servers.packetProcs.IPacketProvider;

/*package_p*/ interface I_Linking_Provider extends IPacketProvider
{
	ILinkHandlerProvider linkHandler(IPacketHandler handler);
	IPacketHandler unlinkHandler(IPacketHandler handler);
	IPacketHandler unlinkHandler();
	
	ILinkHandlerProvider insertHandler(ILinkHandlerProvider handler);
	ILinkHandlerProvider takeoutHandler(ILinkHandlerProvider handler);
	ILinkHandlerProvider takeoutHandler();
}
