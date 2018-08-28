package radua.servers.server.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;

import radua.utils.errors.generic.ImmutableVariable;

public class GenericUdpHandlerNotifier implements IUdpHandler, IUdpNotifier
{
	private IUdpNotifier notifier;
	private IUdpHandler handler;
	private boolean isRunning;
	
	public void setNotifier(IUdpNotifier nNotifier) throws ImmutableVariable { 
		if (notifier != null) throw new ImmutableVariable(this, "notifier");
		notifier = nNotifier; 
		}
	public void setHandler(IUdpHandler nHandler) { handler = nHandler; }
	
	
	public boolean isRunning() { return  isRunning; }
	public boolean start()
	{
		if (isRunning) return false;
		return true;
	}
	public boolean stop()
	public boolean stopWait()
	
	
	public boolean startNotifier()
	{
		boolean ret;
		notifier.startNotifier();
		privateStart();
		return ret;
	}
	public boolean stopNotifier()
	{
		boolean ret;
		notifier.stopNotifier();
		privateStop();
		return ret;
	}
	public boolean stopWaitNotifier()
	{
		boolean ret;
		notifier.stopWaitNotifier();
		privateStopWait();
		return ret;
	}

	
	public boolean startHandler()
	{
		boolean ret = !isRunning;
		if (!isRunning) { privateStart(); }
		isRunning = true;
		handler.startHandler();
		return ret;
	}
	
	public boolean stopHandler()
	{
		boolean ret = isRunning;
		if (isRunning) { privateStop(); }
		isRunning = false;
		handler.stopHandler();
		return ret;
	}
	public boolean stopWaitHandler()
	{
		boolean ret = isRunning;
		if (isRunning) { privateStopWait(); }
		isRunning = false;
		handler.stopWaitHandler();
		return ret;
	}

	
	private void privateStart() { }
	private void privateStop() { }
	private void privateStopWait() { }
	
	
	public  void handlePacket(DatagramPacket packet);
	public  void sendPacket(byte[] data, SocketAddress remoteAddr) throws IOException;
}
