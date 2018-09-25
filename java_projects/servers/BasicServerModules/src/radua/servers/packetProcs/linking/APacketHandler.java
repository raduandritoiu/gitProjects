package radua.servers.packetProcs.linking;

public abstract class APacketHandler extends A_Linking_Base implements I_Linking_Inner
{
	public final void setProvider(IOuter outer) { mOuter = outer; }
	public final IOuter getProvider() { return mOuter; }
	
	public ILinkInnerOuter linkOuter(IOuter outer) { return super.pp_linkOuter(outer); }
	public IOuter unlinkOuter(IOuter outer) { return super.pp_unlinkOuter(outer); }
	public IOuter unlinkOuter() { return super.pp_unlinkOuter(); }
	
	
	
	public ILinkInnerOuter insertOuter(ILinkInnerOuter outer) { return null; }
	public ILinkInnerOuter takeoutOuter(ILinkInnerOuter outer) { return null; }
	public ILinkInnerOuter takeoutOuter() { return null; }
}
