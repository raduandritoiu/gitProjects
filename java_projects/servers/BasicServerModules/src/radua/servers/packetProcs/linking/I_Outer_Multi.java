package radua.servers.packetProcs.linking;

/*package_p*/ interface I_Outer_Multi extends IOuter
{
	boolean hasInner(IInner inner);
	IInner addInner(IInner inner, int pos);
	IInner removeInner(IInner inner);
	IInner getInner(int pos);
	int getInnerPos(IInner inner);
	int getInnersNum();
}
