package radua.servers.server.udp;

import java.net.DatagramPacket;

import radua.utils.errors.generic.ImmutableVariable;

public interface IUdpHandler 
{
	boolean isRunning();
	boolean start();
	boolean stop();
	boolean stopWait();
	
	/*package_protected*/ boolean startHandler();
	/*package_protected*/ boolean stopHandler();
	/*package_protected*/ boolean stopWaitHandler();
	
	/*package_protected*/ void setNotifier(IUdpNotifier notifier) throws ImmutableVariable;
	/*package_protected*/ void handlePacket(DatagramPacket packet);
}
