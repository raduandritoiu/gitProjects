package radua.servers.packetProcs.linking;

/*package_p*/ interface I_Linking_Inner_Multi extends IInner
{
	IOuter addOuter(IOuter outer, int pos);
	IOuter removeOuter(IOuter outer);
	IOuter getOuter(int pos);
	int getOuterPos(IOuter outer);
	int getOutersNum();
}
