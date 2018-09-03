package radua.servers.server.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import radua.servers.server.generics.APacketProviderHandler;
import radua.servers.server.generics.IPacketHandler;
import radua.utils.errors.generic.ImmutableVariable;
import radua.utils.logs.Log;


public class ParallelPacketProviderHandler extends APacketProviderHandler
{
	private final int numThreads;
	private ExecutorService pool;
	
	
	public ParallelPacketProviderHandler(int nNumThreads, IPacketHandler nHandler) throws ImmutableVariable
	{
		numThreads = nNumThreads;
		setHandler(nHandler);
		nHandler.setProvider(this);
	}
	
	
	protected void internalStart()
	{
		pool = Executors.newFixedThreadPool(numThreads);
	}
	protected void internalStop()
	{
	    pool.shutdown();
	}
	protected void internalStopWait()
	{
		internalStop();
		try { pool.awaitTermination(10, TimeUnit.SECONDS); }
	    catch (Exception ex) {
	    	Log._err("Error waiting closing POOL", ex);
	    }
	}
	
	
	public void transmitPacket(byte[] data, SocketAddress remoteAddr) throws IOException
	{
		getProvider().transmitPacket(data, remoteAddr);
	}
	public void handlePacket(DatagramPacket packet)
	{
		pool.execute(new ParallelProcessPacket(packet));
	}


	private class ParallelProcessPacket implements Runnable
	{
		public final DatagramPacket packet;
		public ParallelProcessPacket(DatagramPacket nPacket)
		{
			packet = nPacket;
		}
		
		public void run()
		{
			getHandler().handlePacket(packet);
		}
	}
}
