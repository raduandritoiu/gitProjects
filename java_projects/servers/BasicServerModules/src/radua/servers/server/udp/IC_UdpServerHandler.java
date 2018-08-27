package radua.servers.server.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;

/* Created (and use) this class instead of an interface because these type of Classes will never need to extend 
 * (or use) the behavior of another Type of Class and the most important reason, I need those methods to be 
 * PACKAGE_PROTECTED or at least PROTECTED, not PUBLIC as the case would be when coding with an interface.*/
public class IC_UdpServerHandler {
	/*package_protected*/ boolean serverStarted() { return true; }
	/*package_protected*/ boolean serverStopped() { return true; }
	/*package_protected*/ boolean serverStoppedWait() { return true; }
	
	public boolean start() { return true; }
	public boolean stop() { return true; }
	public boolean stopWait() { return true; }
	public boolean isRunning() { return true; }
	
	/*package_protected*/ void sendPacket(byte[] data, SocketAddress remoteAddr) throws IOException {}
	/*package_protected*/ void handleRecvPkt(DatagramPacket packet) {}
}
