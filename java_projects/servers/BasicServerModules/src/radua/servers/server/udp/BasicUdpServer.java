package radua.servers.server.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;

import radua.servers.server.generics.ARunPacketProvider;
import radua.servers.server.generics.IPacketHandler;
import radua.servers.server.generics.IPacketProvider;
import radua.servers.server.generics.IServer;
import radua.utils.errors.generic.ImmutableVariable;
import radua.utils.logs.Log;


public class BasicUdpServer extends ARunPacketProvider implements IServer, IPacketProvider
{
	private final DatagramSocket listenSock;
	private final SocketAddress localAddr;
	private final ListenThread listenThread;
	
	
	public BasicUdpServer(IPacketHandler nHandler, SocketAddress nLocalAddr)
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
		try { 
			setHandler(nHandler);
			nHandler.setProvider(this);
		}
		catch (ImmutableVariable ex) { /* */ }
	}
	
	
	public SocketAddress getLocalAddr() { return localAddr; }
	
	
	protected void internalStart()
	{
		Log._out("Basic UDP Server started on address " + localAddr);
		listenThread.start();
	}
	protected void internalStop()
	{
		listenSock.close();
	}
	protected void internalStopWait()
	{
		internalStop();
		try { listenThread.join(); }
		catch (Exception ex) { /* */ }
	}
	
	
	public final void transmitPacket(byte[] data, SocketAddress remoteAddr) throws IOException
	{
		DatagramPacket packet = new DatagramPacket(data, data.length, remoteAddr);
		listenSock.send(packet);
	}

	private void receivedPacket(DatagramPacket packet)
	{
		getHandler().handlePacket(packet);
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
			while (isRunning)
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