package radua.servers.server.udp;

import java.net.DatagramPacket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class GenericUdpServer_vers3 extends GenericUdpServer
{	
	private ExecutorService execPool;
	
	public GenericUdpServer_vers3(SocketAddress nLocalAddr, int nNumThreads)
	{
		super(nLocalAddr, nNumThreads);
	}
	
	
	
	public void start()
	{
		super.start();
		execPool = Executors.newFixedThreadPool(getNumThreads());
	}
	
	public void stop()
	{
		super.stop();
		execPool.shutdown();
	}
	
	public void stopWait()
	{
		super.stopWait();
		try { execPool.awaitTermination(2, TimeUnit.MINUTES); }
		catch (Exception ex) {
		}
	}
	
	
	protected void handlePackets(DatagramPacket packet)
	{
		// handle the received packet
		ParallelPacketHandler packetHandler = new ParallelPacketHandler(packet);
		execPool.submit(packetHandler);
	}
	
	
	protected void handleParallelPackets(DatagramPacket pkt)
	{}
	
	
	
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
			handleParallelPackets(packet);
		}
	}
}