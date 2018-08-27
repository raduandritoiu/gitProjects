package radua.servers.server.udp;

import java.io.IOException;
import java.net.SocketAddress;

public interface IUdpNotifier 
{
	boolean isRunning();
	boolean start();
	boolean stop();
	boolean stopWait();
	
	/*package_protected*/ boolean startNotifier();
	/*package_protected*/ boolean stopNotifier();
	/*package_protected*/ boolean stopWaitNotifier();
	
	/*package_protected*/ void setHandler(IUdpHandler handler);
	/*package_protected*/ void sendPacket(byte[] data, SocketAddress remoteAddr) throws IOException;
	
}
