package radua.servers.packetProcs.helpers;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.basics.APacketProviderHandler;
import radua.utils.logs.Log;


public class ParallelPacketProviderHandler extends APacketProviderHandler
{
	private final int numThreads;
	private ExecutorService pool;
	
	
	public ParallelPacketProviderHandler(int nNumThreads)
	{
		numThreads = nNumThreads;
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
	
	
	public void transmitPacket(IPacket packet) throws IOException
	{
		getProvider().transmitPacket(packet);
	}
	public void handlePacket(IPacket packet)
	{
		pool.execute(new ParallelProcessPacket(packet));
	}


	private class ParallelProcessPacket implements Runnable
	{
		public final IPacket packet;
		public ParallelProcessPacket(IPacket nPacket)
		{
			packet = nPacket;
		}
		
		public void run()
		{
			getHandler().handlePacket(packet);
		}
	}
}
