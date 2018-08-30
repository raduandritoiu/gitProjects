package radua.servers.server.generics;

import java.net.DatagramPacket;

import radua.utils.errors.generic.ImmutableVariable;

public interface IPacketHandler// extends IRunnable
{
	/*package_p*/ boolean startHandler();
	/*package_p*/ boolean stopHandler();
	/*package_p*/ boolean stopWaitHandler();
	
	void setProvider(IPacketProvider provider) throws ImmutableVariable;
	void handlePacket(DatagramPacket packet);
}
