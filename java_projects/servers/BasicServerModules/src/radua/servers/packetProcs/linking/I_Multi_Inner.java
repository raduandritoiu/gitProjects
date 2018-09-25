package radua.servers.packetProcs.linking;

/*package_p*/ interface I_Multi_Inner extends IInner
{
	boolean hasOuter(IOuter outer);
	IOuter addOuter(IOuter outer, int pos);
	IOuter removeOuter(IOuter outer);
	IOuter getOuter(int pos);
	int getOuterPos(IOuter outer);
	int getOutersNum();
}
