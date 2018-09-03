package testing;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;

import radua.servers.server.generics.IServer;
import radua.servers.server.udp.BasicUdpServer;
import radua.servers.server.udp.EchoPacketHandler;
import radua.servers.server.udp.HostSessionPacketProviderHandler;
import radua.servers.server.udp.LogPacketProviderHandler;
import radua.servers.server.udp.ParallelPacketProviderHandler;
import radua.servers.server.udp.SMPacketHandler;
import radua.utils.logs.Log;
import radua.utils.logs.SilentLog;

public class ServerTesting 
{
	public static void run() throws Exception
	{
		String ipAddr = "192.168.100.3";
//		String ipAddr = "10.114.197.141";
		SocketAddress srvAddr = new InetSocketAddress(ipAddr, 3333);
		SocketAddress addr1 = new InetSocketAddress(ipAddr, 4000);
		SocketAddress addr2 = new InetSocketAddress(ipAddr, 5000);
		SocketAddress addr3 = new InetSocketAddress(ipAddr, 6000);
		
//		Log log = new Log();
		Log log = new SilentLog();
		
//		IServer srv = new BasicUdpServer(new LogPacketProviderHandler(log, new EchoPacketHandler()), srvAddr);
//		IServer srv = new BasicUdpServer(new LogPacketProviderHandler(log, new SMPacketHandler()), srvAddr);
//		IServer srv = new BasicUdpServer(new ParallelPacketProviderHandler(2, 
//				new LogPacketProviderHandler(log, new SMPacketHandler())), srvAddr);

		IServer srv = new BasicUdpServer(new ParallelPacketProviderHandler(2, new HostSessionPacketProviderHandler(
				new LogPacketProviderHandler(log, new SMPacketHandler()))), srvAddr);

		
		srv.start();
		
		
		ClientThread cl1 = new ClientThread(new Log(""), "Client_1", addr1, srvAddr);
		ClientThread cl2 = new ClientThread(new Log("\t\t\t\t\t"), "Client_2", addr2, srvAddr);
		cl1.start();
		cl2.start();
		cl1.join();
		cl2.join();
		
		srv.stopWait();
	}
	
	
	public static class ClientThread extends Thread
	{
		private DatagramSocket sock;
		private Log log;
		
		public ClientThread(Log nlog, String nName, SocketAddress nLocalAddr, SocketAddress nDestAddr) throws SocketException
		{
			log = nlog;
			sock = new DatagramSocket(nLocalAddr);
			sock.connect(nDestAddr);
			setName(nName);
		}
		
		public void run()
		{
			log.out(getName() + " Start");
		    
		    String msg = "Buna eu sunt " + getName() + ". tu cine esti?";
			runStep(msg);
			
			msg = "Ce mai faci?";
			runStep(msg);
			
			msg = "Cati ani ai?";
			runStep(msg);
			
			log.out(getName() + " Stop");
		}
		
		
		private void runStep(String msg)
		{
			log.out(getName() + " sends: " + msg);
			write(msg);
			msg = read();
			log.out(getName() + " received: " + msg);
		}
		
		private String read()
		{
		    byte[] buf = new byte[100];
		    DatagramPacket packet = new DatagramPacket(buf, buf.length);
		    try {
		    	sock.receive(packet);
		    	String str = new String(packet.getData(), 0, packet.getLength());
		    	return str;
		    }
		    catch (Exception ex) { log.err("Error reading SOCKET", ex); }
		    return null;
		}
		
		private void write(String msg)
		{
			DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.getBytes().length);
			try { sock.send(packet); }
			catch (Exception ex) { log.err("Error reading SOCKET", ex); }
		}
	}
}
