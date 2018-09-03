package radua.servers.server.generics.old;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;

import radua.servers.server.generics.IServer;


public class BasicUdpServer_old implements IServer, IPacketProvider_old
{
	private final DatagramSocket listenSock;
	private final SocketAddress localAddr;
	private final ListenThread listenThread;
	private IPacketHandler_old handler;
	private boolean isRunning;
	
	
	public BasicUdpServer_old(IPacketHandler_old nHandler, SocketAddress nLocalAddr)
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
	public void setHandler(IPacketHandler_old nHandler) { handler = nHandler; }
	
	
	public boolean start()
	{
		if (isRunning) return false;
		handler.startHandler();
		startProvider();
		return true;
	}
	
	public boolean stop()
	{
		if (!isRunning) return false;
		handler.stopHandler();
		stopProvider();
		return true;
	}
	
	public boolean stopWait()
	{
		if (!isRunning) return false;
		handler.stopWaitHandler();
		stopWaitProvider();
		return true;
	}
	
	public boolean startProvider()
	{
		if (isRunning) return false;
		isRunning = true;
		listenThread.start();
		return true;
	}
	
	public boolean stopProvider()
	{
		if (!isRunning) return false;
		listenSock.close();
		isRunning = false;
		return true;
	}
	
	public boolean stopWaitProvider()
	{
		if (!isRunning) return false;
		stopProvider();
		try { listenThread.join(); }
		catch (Exception ex) {
			//
		}
		return true;
	}
	
	
	/*should be protected*/ 
	public final void transmitPacket(byte[] data, SocketAddress remoteAddr) throws IOException
	{
		DatagramPacket packet = new DatagramPacket(data, data.length, remoteAddr);
		listenSock.send(packet);
	}

	
	protected void receivedPacket(DatagramPacket packet)
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
				receivedPacket(packet);
			 }
		}
	}
}