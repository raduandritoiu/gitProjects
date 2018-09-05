package radua.servers.server.generics;

import java.net.DatagramPacket;

import radua.utils.errors.generic.ImmutableVariable;

public interface IPacketHandler 
{
	IPacketProviderHandler linkProvider(IPacketProvider provider) throws ImmutableVariable;
	void setProvider(IPacketProvider provider) throws ImmutableVariable;

	/** This function should not be used outside of Provider / Handler context. */
	void handlePacket(DatagramPacket packet);
}
