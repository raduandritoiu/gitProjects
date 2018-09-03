package testing;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;

import radua.servers.server.generics.IServer;
import radua.servers.server.udp.BasicUdpServer;
import radua.servers.server.udp.SMPacketHandler;

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
		
		
//		IServer srv = new BasicUdpServer(new EchoPacketHandler(), srvAddr);
		IServer srv = new BasicUdpServer(new SMPacketHandler(), srvAddr);
		srv.start();
		
		
		ClientThread cl1 = new ClientThread("\tClient_1", addr1, srvAddr);
		ClientThread cl2 = new ClientThread("\tClient_2", addr2, srvAddr);
		cl1.start();
		cl2.start();
		cl1.join();
		cl2.join();
		
		srv.stopWait();
	}
	
	
	public static class ClientThread extends Thread
	{
		private DatagramSocket sock;

		public ClientThread(String NName, SocketAddress nLocalAddr, SocketAddress nDestAddr) throws SocketException
		{
			sock = new DatagramSocket(nLocalAddr);
			sock.connect(nDestAddr);
			setName(NName);
		}
		
		public void run()
		{
			System.out.println(getName() + " Start");
			
		    
		    String msg = "Buna eu sunt " + getName() + ". tu cine esti?";
			System.out.println("\n" + getName() + " sends: " + msg);
			write(msg);
			msg = read();
			System.out.println(getName() + " received: " + msg);
			
			msg = "Ce mai faci?";
			System.out.println("\n" + getName() + " sends: " + msg);
			write(msg);
			msg = read();
			System.out.println(getName() + " received: " + msg);
			
			msg = "Cati ani ai?";
			System.out.println("\n" + getName() + " sends: " + msg);
			write(msg);
			msg = read();
			System.out.println(getName() + " received: " + msg);
			
			
			System.out.println(getName() + " Stop");
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
		    catch (Exception ex) { System.out.println("Error reading SOCKET: " + ex); }
		    return null;
		}
		
		private void write(String msg)
		{
			DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.getBytes().length);
			try { sock.send(packet); }
			catch (Exception ex) { System.out.println("Error reading SOCKET: " + ex); }
		}
	}
}
