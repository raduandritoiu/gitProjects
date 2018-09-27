package radua.servers.packetProcs.linking;

public abstract class APacketOuter_S extends A_Linking_Base implements I_Linking_Outer_Single
{
	public final void setInner(IInner inner) { mInner = inner; }
	public final IInner getInner() { return mInner; }
	
	public IMiddle linkInner(IInner inner) { return super.pp_linkInner(inner); }
	public IInner unlinkInner(IInner inner) { return super.pp_unlinkInner(inner); }
	public IInner unlinkInner() { return super.pp_unlinkInner(); }
	
	public IMiddle insertInner(IMiddle inner) { return super.pp_single_insertInner(inner); }
	

	// ==================
	public IMiddle takeoutInner(IMiddle inner) { return null; }
	public IMiddle takeoutInner() { return null; }
}
