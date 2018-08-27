package radua.servers.server.udp;

import java.net.DatagramPacket;

public interface IUdpHandler 
{
	boolean isRunning();
	boolean start();
	boolean stop();
	boolean stopWait();
	
	/*package_protected*/ boolean startHandler();
	/*package_protected*/ boolean stopHandler();
	/*package_protected*/ boolean stopWaitHandler();
	
	/*package_protected*/ void setNotifier(IUdpNotifier notifier);
	/*package_protected*/ void handlePacket(DatagramPacket packet);
}
