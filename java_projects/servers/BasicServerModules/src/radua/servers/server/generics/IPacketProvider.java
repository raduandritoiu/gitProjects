package radua.servers.server.generics;

import java.io.IOException;
import java.net.SocketAddress;

import radua.utils.errors.generic.ImmutableVariable;

public interface IPacketProvider// extends IRunnable
{
	/*package_p*/ boolean startProvider();
	/*package_p*/ boolean stopProvider();
	/*package_p*/ boolean stopWaitProvider();
	
	void setHandler(IPacketHandler handler) throws ImmutableVariable;
	//TODO: need to rename this function
	/*protected*/ void sendPacket(byte[] data, SocketAddress remoteAddr) throws IOException;
}
