package radua.servers.packetProcs.linking;

import radua.servers.packetProcs.IPacketHandler;

public interface IInner extends IPacketHandler
{
	IMiddle linkOuter(IOuter outer);
	IOuter unlinkOuter(IOuter outer);
	IOuter unlinkOuter();
	
	IMiddle insertOuter(IMiddle outer);
	IMiddle takeoutOuter(IMiddle outer);
	IMiddle takeoutOuter();
}
