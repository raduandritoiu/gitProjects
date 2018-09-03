package radua.servers.server.generics.old;

import java.io.IOException;
import java.net.SocketAddress;

import radua.utils.errors.generic.ImmutableVariable;

public interface IPacketProvider_old
{
	/*package_p*/ boolean startProvider();
	/*package_p*/ boolean stopProvider();
	/*package_p*/ boolean stopWaitProvider();
	
	void setHandler(IPacketHandler_old handler) throws ImmutableVariable;
	//TODO: need to rename this function
	/*protected*/ void transmitPacket(byte[] data, SocketAddress remoteAddr) throws IOException;
}
