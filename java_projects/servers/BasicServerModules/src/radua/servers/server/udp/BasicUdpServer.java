package radua.servers.server.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;

import radua.servers.packetProcs.Packet;
import radua.servers.packetProcs.IPacket;
import radua.servers.packetProcs.PacketDirection;
import radua.servers.packetProcs.linking.ARunPacketOuter_S;
import radua.servers.packetProcs.linking.IOuter;
import radua.servers.server.IServer;
import radua.utils.logs.Log;


public class BasicUdpServer extends ARunPacketOuter_S implements IServer
{
	private final DatagramSocket listenSock;
	private final SocketAddress localAddr;
	private final ListenThread listenThread;
	
	
	public BasicUdpServer(SocketAddress nLocalAddr)
	{
		DatagramSocket tmpSock = null;
		SocketAddress tmpAddr = nLocalAddr;
		try {
			tmpSock = new DatagramSocket(nLocalAddr);
			tmpAddr = tmpSock.getLocalSocketAddress();
		}
		catch (Exception ex) { /* */ }
		listenSock = tmpSock;
		localAddr = tmpAddr;
		listenThread = new ListenThread();
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
	
	
	/*should be protected*/
	public final boolean transmitPacket(IPacket packet) throws IOException
	{
		DatagramPacket dp = new DatagramPacket(packet.data(), packet.data().length, packet.remoteAddr());
		listenSock.send(dp);
		return true;
	}

	private void receivedPacket(DatagramPacket packet)
	{
		getInner().handlePacket(new UdpPacket(packet));
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
			while (isRunning())
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
	
	
	private class UdpPacket extends Packet
	{
		public UdpPacket(DatagramPacket packet)
		{
			super(packet.getData(), packet.getLength(), packet.getSocketAddress(), PacketDirection.INCOMING);
		}
	}
}