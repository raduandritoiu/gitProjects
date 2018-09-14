package radua.servers.packetProcs.linking;

import radua.servers.packetProcs.IPacketHandler;
import radua.servers.packetProcs.IPacketProvider;

/*package_p*/ interface I_Linking_Handler extends IPacketHandler
{
	ILinkHandlerProvider linkProvider(IPacketProvider provider);
	IPacketProvider unlinkProvider(IPacketProvider provider);
	IPacketProvider unlinkProvider();
	
	ILinkHandlerProvider insertProvider(ILinkHandlerProvider provider);
	ILinkHandlerProvider takeoutProvider(ILinkHandlerProvider provider);
	ILinkHandlerProvider takeoutProvider();
}
