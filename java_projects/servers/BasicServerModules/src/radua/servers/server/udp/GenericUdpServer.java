package radua.servers.server.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;

import radua.servers.server.interfaces.IGenericServer;


public class GenericUdpServer implements IGenericServer
{	
	
	private final SocketAddress localAddr;
	private final DatagramSocket listenSock;
	
	private final ListenThread listenThread;
	private boolean isRunning;
	
	
	public GenericUdpServer(SocketAddress nLocalAddr)
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
		listenSock = tmpSock;
		localAddr = tmpAddr;
		listenThread = new ListenThread();
	}
	
	
	public SocketAddress getLocalAddr() { return localAddr; }
	public boolean isRunning() { return isRunning; }

	
	public boolean start()
	{
		if (isRunning) return false;
		isRunning = true;
		listenThread.start();
		return true;
	}
	
	public boolean stop()
	{
		if (!isRunning) return false;
		listenSock.close();
		isRunning = false;
		return true;
	}
	
	public boolean stopWait()
	{
		if (!isRunning) return false;
		stop();
		try { listenThread.join(); }
		catch (Exception ex) {
			//
		}
		return true;
	}
	
	
	/*package_protected*/ void sendPacket(byte[] data, SocketAddress remoteAddr) throws IOException
	{
		DatagramPacket packet = new DatagramPacket(data, data.length, remoteAddr);
		listenSock.send(packet);
	}

	
	/*package_protected*/  void handleRecvPkt(DatagramPacket packet)
	{}
	
	
	
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
				handleRecvPkt(packet);
			 }
		}
	}
}