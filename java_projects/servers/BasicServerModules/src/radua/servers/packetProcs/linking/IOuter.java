package radua.servers.packetProcs.linking;

import radua.servers.packetProcs.IPacketProvider;

public interface IOuter extends IPacketProvider
{
	IMiddle linkInner(IInner inner);
	IInner unlinkInner(IInner inner);
	IInner unlinkInner();
	
	IMiddle insertInner(IMiddle inner);
	IMiddle takeoutInner(IMiddle inner);
	IMiddle takeoutInner();
}
