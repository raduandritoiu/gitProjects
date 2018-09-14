package radua.servers.packetProcs.linking;

import radua.servers.packetProcs.IPacketProvider;

public abstract class APacketHandler extends A_Linking_Base implements I_Linking_Handler
{
	public final void setProvider(IPacketProvider nProvider) { provider = nProvider; }
	public final IPacketProvider getProvider() { return provider; }
	
	public ILinkHandlerProvider linkProvider(IPacketProvider provider) { return super.pp_linkProvider(provider); }
	public IPacketProvider unlinkProvider(IPacketProvider provider) { return super.pp_unlinkProvider(provider); }
	public IPacketProvider unlinkProvider() { return super.pp_unlinkProvider(); }
	
	
	
	public ILinkHandlerProvider insertProvider(ILinkHandlerProvider provider) { return null; }
	public ILinkHandlerProvider takeoutProvider(ILinkHandlerProvider provider) { return null; }
	public ILinkHandlerProvider takeoutProvider() { return null; }
}
