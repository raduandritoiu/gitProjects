package radua.servers.packetProcs.linking;

/*package_p*/ interface I_Linking_Outer_Multi extends IOuter
{
	IInner addInner(IInner inner, int pos);
	IInner removeInner(IInner inner);
	IInner getInner(int pos);
	int getInnerPos(IInner inner);
	int getInnersNum();
}
