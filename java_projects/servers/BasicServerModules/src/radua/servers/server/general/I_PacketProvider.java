package radua.servers.server.general;

import java.io.IOException;
import java.net.SocketAddress;

import radua.utils.errors.generic.ImmutableVariable;


public interface I_PacketProvider 
{
	void setHandler(I_PacketHandler handler) throws ImmutableVariable;
	//TODO: need to rename this function
	void sendPacket(byte[] data, SocketAddress remoteAddr) throws IOException;
}
