package radua.servers.packetProcs.linking;

public abstract class APacketInner_M extends A_Linking_Base implements I_Linking_Inner_Multi
{
	public APacketInner_M() { super.pp_initOuters(); }
	
	public IOuter addOuter(IOuter outer, int pos) { return super.pp_addOuter(outer, pos); }
	public IOuter removeOuter(IOuter outer) { return super.pp_removeOuter(outer); }
	public IOuter getOuter(int pos) { return super.pp_getOuter(pos); }
	public int getOuterPos(IOuter outer) { return super.pp_getOuterPos(outer); }
	public int getOutersNum() { return super.pp_getOuterNum(); }
	
	
	public IMiddle linkOuter(IOuter outer) { return super.pp_linkOuter(outer); }
	public IOuter unlinkOuter(IOuter outer) { return super.pp_unlinkOuter(outer); }
	public IOuter unlinkOuter() { return super.pp_unlinkOuter(); }
	
	
	public IMiddle insertOuter(IMiddle outer) { return super.pp_single_insertOuter(outer); }
	
	
	// ==================
	public IMiddle takeoutOuter(IMiddle outer) { return null; }
	public IMiddle takeoutOuter() { return null; }
}
