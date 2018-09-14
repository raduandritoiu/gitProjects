package radua.servers.packetProcs.linking;

import radua.servers.packetProcs.IPacketHandler;
import radua.servers.packetProcs.IPacketProvider;

public abstract class APacketHandlerProvider extends A_Linking_Base implements ILinkHandlerProvider
{
	public final void setProvider(IPacketProvider nProvider) { provider = nProvider; }
	public final IPacketProvider getProvider() { return provider; }
	
	public final void setHandler(IPacketHandler nHandler) { handler = nHandler; }
	public final IPacketHandler getHandler() { return handler; }
	
	public ILinkHandlerProvider linkProvider(IPacketProvider provider) { return super.pp_linkProvider(provider); }
	public IPacketProvider unlinkProvider(IPacketProvider provider) { return super.pp_unlinkProvider(provider); }
	public IPacketProvider unlinkProvider() { return super.pp_unlinkProvider(); }

	public ILinkHandlerProvider linkHandler(IPacketHandler handler) { return super.pp_linkHandler(handler); }
	public IPacketHandler unlinkHandler(IPacketHandler handler) { return super.pp_unlinkHandler(handler); }
	public IPacketHandler unlinkHandler() { return super.pp_unlinkHandler(); }
	

	
	
	public ILinkHandlerProvider insertProvider(ILinkHandlerProvider provider) { return null; }
	public ILinkHandlerProvider takeoutProvider(ILinkHandlerProvider provider) { return null; }
	public ILinkHandlerProvider takeoutProvider() { return null; }

	public ILinkHandlerProvider insertHandler(ILinkHandlerProvider handler) { return null; }
	public ILinkHandlerProvider takeoutHandler(ILinkHandlerProvider handler) { return null; }
	public ILinkHandlerProvider takeoutHandler() { return null; }
	
	public ILinkHandlerProvider takeout() { return null; }
}
