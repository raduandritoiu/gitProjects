package radua.servers.packetProcs.linking;

/*package_p*/ interface I_Single_Inner extends IInner
{
	void setOuter(IOuter outer);
	IOuter getOuter();
}
