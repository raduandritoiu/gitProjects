package radua.servers.packetProcs;

import java.io.IOException;

import radua.utils.errors.generic.ImmutableVariable;

public interface IPacketProvider 
{
	IPacketProviderHandler linkHandler(IPacketHandler handler) throws ImmutableVariable;
	void setHandler(IPacketHandler handler) throws ImmutableVariable;
	
	//TODO: need to rename this function
	/** This function should not be used outside of Provider / Handler context. */
	void transmitPacket(IPacket packet) throws IOException;
}
