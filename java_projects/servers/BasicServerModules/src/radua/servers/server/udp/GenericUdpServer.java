package radua.servers.server.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;

import radua.servers.server.interfaces.IGenericServer;


public class GenericUdpServer implements IGenericServer, IUdpNotifier
{
	private final DatagramSocket listenSock;
	private final SocketAddress localAddr;
	private final ListenThread listenThread;
	private IUdpHandler handler;
	private boolean isRunning;
	
	
	public GenericUdpServer(IUdpHandler nHandler, SocketAddress nLocalAddr)
	{
		DatagramSocket tmpSock = null;
		SocketAddress tmpAddr = nLocalAddr;
		try {
			tmpSock = new DatagramSocket(nLocalAddr);
			tmpAddr = tmpSock.getLocalSocketAddress();
		}
		catch (Exception ex) {
			//
		}
		handler = nHandler;
		listenSock = tmpSock;
		localAddr = tmpAddr;
		listenThread = new ListenThread();
	}
	
	
	public SocketAddress getLocalAddr() { return localAddr; }
	public boolean isRunning() { return isRunning; }
	public void setHandler(IUdpHandler nHandler) { handler = nHandler; }
	
	
	public boolean start()
	{
		if (isRunning) return false;
		handler.startHandler();
		startNotifier();
		return true;
	}
	
	public boolean stop()
	{
		if (!isRunning) return false;
		handler.stopHandler();
		stopNotifier();
		return true;
	}
	
	public boolean stopWait()
	{
		if (!isRunning) return false;
		handler.stopWaitHandler();
		stopWaitNotifier();
		return true;
	}
	
	public boolean startNotifier()
	{
		if (isRunning) return false;
		isRunning = true;
		listenThread.start();
		return true;
	}
	
	public boolean stopNotifier()
	{
		if (!isRunning) return false;
		listenSock.close();
		isRunning = false;
		return true;
	}
	
	public boolean stopWaitNotifier()
	{
		if (!isRunning) return false;
		stopNotifier();
		try { listenThread.join(); }
		catch (Exception ex) {
			//
		}
		return true;
	}
	
	
	/*should be package_protected*/ 
	public final void sendPacket(byte[] data, SocketAddress remoteAddr) throws IOException
	{
		DatagramPacket packet = new DatagramPacket(data, data.length, remoteAddr);
		listenSock.send(packet);
	}

	
	/*package_protected*/ void internalHandlePacket(DatagramPacket packet)
	{
		handler.handlePacket(packet);
	}
	
	
	
//==========================================================================
//==========================================================================
	private class ListenThread extends Thread
	{
		public ListenThread()
		{}
	 
		public void run()
		{
			setName("ListenThread");
			while(isRunning)
			{
				// read
				byte[] buf = new byte[MAX_PDU];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				try {
					listenSock.receive(packet);
				}
				catch (Exception ex) {
					//
					continue;
				}
	
				// handle the received packet
				internalHandlePacket(packet);
			 }
		}
	}
}