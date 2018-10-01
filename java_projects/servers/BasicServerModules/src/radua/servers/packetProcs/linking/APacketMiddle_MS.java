package radua.servers.packetProcs.linking;

public abstract class APacketMiddle_MS extends A_Linking_Base implements I_Linking_Middle_Multi_Single
{
	public APacketMiddle_MS() { super.pp_initOuters(); }
	
	
	public IOuter addOuter(IOuter outer, int pos) { return super.pp_addOuter(outer, pos); }
	public IOuter removeOuter(IOuter outer) { return super.pp_removeOuter(outer); }
	public IOuter getOuter(int pos) { return super.pp_getOuter(pos); }
	public int getOuterPos(IOuter outer) { return super.pp_getOuterPos(outer); }
	public int getOutersNum() { return super.pp_getOuterNum(); }
	
	
	public final void setInner(IInner inner) { mInner = inner; }
	public final IInner getInner() { return mInner; }
	
	
	public IMiddle linkOuter(IOuter outer) { return super.pp_linkOuter(outer); }
	public IOuter unlinkOuter(IOuter outer) { return super.pp_unlinkOuter(outer); }
	public IOuter unlinkOuter() { return super.pp_unlinkOuter(); }

	
	public IMiddle linkInner(IInner inner) { return super.pp_linkInner(inner); }
	public IInner unlinkInner(IInner inner) { return super.pp_unlinkInner(inner); }
	public IInner unlinkInner() { return super.pp_unlinkInner(); }
	
	
	public IMiddle insertOuter(IMiddle outer) { return super.pp_single_insertOuter(outer); }
	public IMiddle insertInner(IMiddle inner) { return super.pp_single_insertInner(inner); }
	
	
	// ==================
	public IMiddle takeoutOuter(IMiddle outer) { return null; }
	public IMiddle takeoutOuter() { return null; }

	public IMiddle takeoutInner(IMiddle inner) { return null; }
	public IMiddle takeoutInner() { return null; }
	
	public IMiddle takeout() { return null; }
}
