package radua.servers.packetProcs.linking;

/*package_p*/ interface I_Linking_Inner extends IInner
{
	ILinkInnerOuter linkOuter(IOuter outer);
	IOuter unlinkOuter(IOuter outer);
	IOuter unlinkOuter();
	
	ILinkInnerOuter insertOuter(ILinkInnerOuter outer);
	ILinkInnerOuter takeoutOuter(ILinkInnerOuter outer);
	ILinkInnerOuter takeoutOuter();
}
