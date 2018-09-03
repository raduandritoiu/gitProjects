package radua.servers.server.generics.old;

import java.net.DatagramPacket;

import radua.utils.errors.generic.ImmutableVariable;

public interface IPacketHandler_old
{
	/*package_p*/ boolean startHandler();
	/*package_p*/ boolean stopHandler();
	/*package_p*/ boolean stopWaitHandler();
	
	void setProvider(IPacketProvider_old provider) throws ImmutableVariable;
	void handlePacket(DatagramPacket packet);
}
