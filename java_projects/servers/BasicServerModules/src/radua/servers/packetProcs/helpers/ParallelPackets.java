package radua.servers.packetProcs.helpers;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.linking.APacketMiddle_SS;
import radua.utils.logs.Log;


public class ParallelPackets extends APacketMiddle_SS
{
	private final int mNumThreads;
	private ExecutorService mPool;
	
	
	public ParallelPackets(int nNumThreads)
	{
		mNumThreads = nNumThreads;
	}
	
	
	protected void internalStart()
	{
		mPool = Executors.newFixedThreadPool(mNumThreads);
	}
	protected void internalStop()
	{
	    mPool.shutdown();
	}
	protected void internalStopWait()
	{
		internalStop();
		try {
			mPool.awaitTermination(10, TimeUnit.SECONDS);
		}
	    catch (Exception ex) {
	    	Log._err("Error waiting closing POOL", ex);
	    }
	}
	
	
	public boolean transmitPacket(IPacket packet) throws IOException
	{
		return getOuter().transmitPacket(packet);
	}
	
	public boolean handlePacket(IPacket packet)
	{
		mPool.execute(new ParallelPacketProcess(packet));
		return true;
	}


//=======================================================================================
//=======================================================================================
	
	private class ParallelPacketProcess implements Runnable
	{
		public final IPacket packet;
		public ParallelPacketProcess(IPacket nPacket)
		{
			packet = nPacket;
		}
		
		public void run()
		{
			getInner().handlePacket(packet);
		}
	}
}
