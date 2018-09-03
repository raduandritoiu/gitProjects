package radua.servers.server.generics;

import java.net.DatagramPacket;

import radua.utils.errors.generic.ImmutableVariable;

public interface IPacketHandler 
{
	void setProvider(IPacketProvider provider) throws ImmutableVariable;
	void handlePacket(DatagramPacket packet);
}
