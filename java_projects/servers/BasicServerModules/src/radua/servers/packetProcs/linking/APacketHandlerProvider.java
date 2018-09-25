package radua.servers.packetProcs.linking;

public abstract class APacketHandlerProvider extends A_Linking_Base implements ILinkInnerOuter
{
	public final void setProvider(IOuter nProvider) { mOuter = nProvider; }
	public final IOuter getProvider() { return mOuter; }
	
	public final void setHandler(IInner nHandler) { mInner = nHandler; }
	public final IInner getHandler() { return mInner; }
	
	public ILinkInnerOuter linkOuter(IOuter provider) { return super.pp_linkOuter(provider); }
	public IOuter unlinkOuter(IOuter provider) { return super.pp_unlinkOuter(provider); }
	public IOuter unlinkOuter() { return super.pp_unlinkOuter(); }

	public ILinkInnerOuter linkInner(IInner handler) { return super.pp_linkInner(handler); }
	public IInner unlinkInner(IInner handler) { return super.pp_unlinkInner(handler); }
	public IInner unlinkInner() { return super.pp_unlinkInner(); }
	

	
	
	public ILinkInnerOuter insertOuter(ILinkInnerOuter provider) { return null; }
	public ILinkInnerOuter takeoutOuter(ILinkInnerOuter provider) { return null; }
	public ILinkInnerOuter takeoutOuter() { return null; }

	public ILinkInnerOuter insertInner(ILinkInnerOuter handler) { return null; }
	public ILinkInnerOuter takeoutInner(ILinkInnerOuter handler) { return null; }
	public ILinkInnerOuter takeoutInner() { return null; }
	
	public ILinkInnerOuter takeout() { return null; }
}
