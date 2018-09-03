package radua.servers.server.generics;

import java.io.IOException;
import java.net.SocketAddress;

import radua.utils.errors.generic.ImmutableVariable;


public interface IPacketProvider 
{
	void setHandler(IPacketHandler handler) throws ImmutableVariable;
	
	//TODO: need to rename this function
	/** This function should not be used outside of Provider / Handler context. */
	void transmitPacket(byte[] data, SocketAddress remoteAddr) throws IOException;
}
