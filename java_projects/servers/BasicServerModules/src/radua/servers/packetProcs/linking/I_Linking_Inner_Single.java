package radua.servers.packetProcs.linking;

/*package_p*/ interface I_Linking_Inner_Single extends IInner
{
	void setOuter(IOuter outer);
	IOuter getOuter();
}
