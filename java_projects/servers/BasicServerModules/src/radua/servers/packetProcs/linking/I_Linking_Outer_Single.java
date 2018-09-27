package radua.servers.packetProcs.linking;

/*package_p*/ interface I_Linking_Outer_Single extends IOuter
{
	void setInner(IInner inner);
	IInner getInner();
}
