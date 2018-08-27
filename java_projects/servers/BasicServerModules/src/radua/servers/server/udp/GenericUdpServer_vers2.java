package radua.servers.server.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import radua.servers.server.interfaces.IGenericServer;


public class GenericUdpServer_vers2 implements IGenericServer
{	
	private SocketAddress localAddr;
	private DatagramSocket listenSock;
	
	private ListenThread listenThread;
	private ExecutorService execPool;
	private boolean isRunning;
	private int numThreads;
	
	
	public GenericUdpServer_vers2(SocketAddress nLocalAddr, int nNumThreads)
	{
		numThreads = nNumThreads;
		try {
			listenSock = new DatagramSocket(nLocalAddr);
			localAddr = listenSock.getLocalSocketAddress();
		}
		catch (Exception ex) {
		}
	}
	
	
	public SocketAddress getLocalAddr() { return localAddr; }
	public int getNumThreads() { return numThreads; }
	public boolean isRunning() { return isRunning; }

	
	public void start()
	{
		isRunning = true;
		execPool = Executors.newFixedThreadPool(numThreads);
		listenThread = new ListenThread();
		listenThread.start();
	}
	
	public void stop()
	{
		listenSock.close();
		execPool.shutdown();
		isRunning = false;
	}
	
	public void stopWait()
	{
		stop();
		try { listenThread.join(); }
		catch (Exception ex) {
		}
		try { execPool.awaitTermination(2, TimeUnit.MINUTES); }
		catch (Exception ex) {
		}
	}
	
	
	/*package_protected*/ void sendPacket(byte[] data, SocketAddress remoteAddr) throws IOException
	{
		DatagramPacket packet = new DatagramPacket(data, data.length, remoteAddr);
		listenSock.send(packet);
	}

	
	protected void handlePackets(DatagramPacket packet)
	{}
	
	
	
//==========================================================================
//==========================================================================
	private class ListenThread extends Thread
	{
		private ListenThread()
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
					continue;
				}
	
				// handle the received packet
				ParallelPacketHandler packetHandler = new ParallelPacketHandler(packet);
				execPool.submit(packetHandler);
			 }
		}
	}
	
	
//==========================================================================
//==========================================================================
	private class ParallelPacketHandler implements Runnable
	{
		private final DatagramPacket packet;
		private ParallelPacketHandler(DatagramPacket nPacket) 
		{
			packet = nPacket;
		}
		
		public void run()
		{
			handlePackets(packet);
		}
	}
}