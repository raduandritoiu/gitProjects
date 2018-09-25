package radua.servers.packetProcs.linking;

/*package_p*/ interface I_Linking_Outer extends IOuter
{
	ILinkInnerOuter linkInner(IInner inner);
	IInner unlinkInner(IInner inner);
	IInner unlinkInner();
	
	ILinkInnerOuter insertInner(ILinkInnerOuter inner);
	ILinkInnerOuter takeoutInner(ILinkInnerOuter inner);
	ILinkInnerOuter takeoutInner();
}
