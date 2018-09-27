package radua.servers.packetProcs.linking;

public abstract class APacketOuter_M extends A_Linking_Base implements I_Linking_Outer_Multi
{
	public IInner addInner(IInner inner, int pos) { return super.pp_addInner(inner, pos); }
	public IInner removeInner(IInner inner) { return super.pp_removeInner(inner); }
	public IInner getInner(int pos) { return super.pp_getInner(pos); }
	public int getInnerPos(IInner inner) { return super.pp_getInnerPos(inner); }
	public int getInnersNum() { return super.pp_getInnersNum(); }
	
	
	public IMiddle linkInner(IInner inner) { return super.pp_linkInner(inner); }
	public IInner unlinkInner(IInner inner) { return super.pp_unlinkInner(inner); }
	public IInner unlinkInner() { return super.pp_unlinkInner(); }
	
	
	public IMiddle insertInner(IMiddle inner) { return super.pp_single_insertInner(inner); }
	

	// ==================
	public IMiddle takeoutInner(IMiddle inner) { return null; }
	public IMiddle takeoutInner() { return null; }
}
