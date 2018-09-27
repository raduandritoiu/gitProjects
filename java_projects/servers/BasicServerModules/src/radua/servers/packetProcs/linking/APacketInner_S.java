package radua.servers.packetProcs.linking;

public abstract class APacketInner_S extends A_Linking_Base implements I_Linking_Inner_Single
{
	public final void setOuter(IOuter outer) { mOuter = outer; }
	public final IOuter getOuter() { return mOuter; }
	
	public IMiddle linkOuter(IOuter outer) { return super.pp_linkOuter(outer); }
	public IOuter unlinkOuter(IOuter outer) { return super.pp_unlinkOuter(outer); }
	public IOuter unlinkOuter() { return super.pp_unlinkOuter(); }
	
	public IMiddle insertOuter(IMiddle outer) { return super.pp_single_insertOuter(outer); }
	
	
	// ==================
	public IMiddle takeoutOuter(IMiddle outer) { return null; }
	public IMiddle takeoutOuter() { return null; }
}
