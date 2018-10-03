package radua.servers.packetProcs.helpers;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.IPacketHandler;
import radua.servers.packetProcs.linking.ARunPacketMiddle_SM;
import radua.utils.logs.Log;

public class ParallelHandlers extends ARunPacketMiddle_SM
{
	private final int mNumThreads;
	private ExecutorService mPool;


	public ParallelHandlers(int nNumThreads)
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
		int handlerNum = getInnersNum();
		for (int i = 0; i < handlerNum; i++) {
			mPool.execute(new ParallelHandlerProcess(packet, getInner(i)));
		}
		return true;
	}

	
	
//=======================================================================================
//=======================================================================================
	
	private class ParallelHandlerProcess implements Runnable
	{
		public final IPacket mPacket;
		public final IPacketHandler mHandler;
		
		public ParallelHandlerProcess(IPacket packet, IPacketHandler handler)
		{
			mPacket = packet;
			mHandler = handler;
		}
		
		public void run()
		{
			mHandler.handlePacket(mPacket);
		}
	}

}
