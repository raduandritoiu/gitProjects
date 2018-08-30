package radua.servers.server.general;

import java.net.DatagramPacket;

import radua.utils.errors.generic.ImmutableVariable;

public interface I_PacketHandler 
{
	void setProvider(I_PacketProvider provider) throws ImmutableVariable;
	void handlePacket(DatagramPacket packet);
}
