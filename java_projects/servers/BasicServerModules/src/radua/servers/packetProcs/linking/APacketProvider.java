package radua.servers.packetProcs.linking;

import radua.servers.packetProcs.IPacketHandler;

public abstract class APacketProvider extends A_Linking_Base implements I_Linking_Provider
{
	public final void setHandler(IPacketHandler nHandler) { handler = nHandler; }
	public final IPacketHandler getHandler() { return handler; }
	
	public ILinkHandlerProvider linkHandler(IPacketHandler handler) { return super.pp_linkHandler(handler); }
	public IPacketHandler unlinkHandler(IPacketHandler handler) { return super.pp_unlinkHandler(handler); }
	public IPacketHandler unlinkHandler() { return super.pp_unlinkHandler(); }
	
	
	
	public ILinkHandlerProvider insertHandler(ILinkHandlerProvider handler) { return null; }
	public ILinkHandlerProvider takeoutHandler(ILinkHandlerProvider handler) { return null; }
	public ILinkHandlerProvider takeoutHandler() { return null; }
}
