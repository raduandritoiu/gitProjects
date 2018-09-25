package radua.servers.packetProcs.linking;

public abstract class APacketProvider extends A_Linking_Base implements I_Linking_Outer
{
	public final void setHandler(IInner nHandler) { mInner = nHandler; }
	public final IInner getHandler() { return mInner; }
	
	public ILinkInnerOuter linkInner(IInner handler) { return super.pp_linkInner(handler); }
	public IInner unlinkInner(IInner handler) { return super.pp_unlinkInner(handler); }
	public IInner unlinkInner() { return super.pp_unlinkInner(); }
	
	
	
	public ILinkInnerOuter insertInner(ILinkInnerOuter handler) { return null; }
	public ILinkInnerOuter takeoutInner(ILinkInnerOuter handler) { return null; }
	public ILinkInnerOuter takeoutInner() { return null; }
}
