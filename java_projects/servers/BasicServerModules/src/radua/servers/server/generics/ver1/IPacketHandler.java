package radua.servers.server.generics.ver1;

import java.net.DatagramPacket;

import radua.utils.errors.generic.ImmutableVariable;

public interface IPacketHandler
{
	void setProvider(IPacketProvider provider) throws ImmutableVariable;
	
	/** This function should not be used outside of Provider / Handler context. */
	void handlePacket(DatagramPacket packet);
	
	/*package_p*/ boolean startHandler();
	/*package_p*/ boolean stopHandler();
	/*package_p*/ boolean stopWaitHandler();
}
