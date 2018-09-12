package testing;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;

import radua.servers.client.interfaces.IUdpClient;
import radua.servers.server.IServer;
import radua.servers.server.udp.BasicUdpServer;
import radua.utils.logs.Log;

public class TwoThreadsTesting 
{
	public static void run_1() throws Exception
	{
		String ipAddr = "192.168.100.3";
//		String ipAddr = "10.114.197.141";
		SocketAddress addr1 = new InetSocketAddress(ipAddr, 3333);
		SocketAddress addr2 = new InetSocketAddress(ipAddr, 4444);
		
		TestUdpConnect cl1 = new TestUdpConnect(addr1);
		cl1.connect(addr2);
		Thread one = new ThreadOne(cl1, addr2);
		Thread two = new ThreadTwo(new TestUdpStandard(addr2), addr1);
		
		two.start();
		one.start();
		one.join();
		two.join();
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
			Log._out(getName() + " Start");
			
		    
		    String msg = "Buna eu sunt One. tu cine esti?";
			Log._out(getName() + " sends: " + msg);
			write(msg);
			msg = read();
			Log._out(getName() + " received: " + msg);
			
			msg = "Ce mai faci?";
			Log._out(getName() + " sends: " + msg);
			write(msg);
			msg = read();
			Log._out(getName() + " received: " + msg);
			
			msg = "Cati ani ai?";
			Log._out(getName() + " sends: " + msg);
			write(msg);
			msg = read();
			Log._out(getName() + " received: " + msg);
			
			
			Log._out(getName() + " Stop");
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
		    catch (Exception ex) {
		    	Log._out("Error reading SOCKET: " + ex);
		    }
		    return null;
		}
		
		private void write(String msg)
		{
			DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.getBytes().length);
			try { sock.send(packet); }
			catch (Exception ex) {
				Log._out("Error reading SOCKET: " + ex);
		    }
		}
	}
	
	
	
	public static class ThreadOne extends Thread
	{
		private IUdpClient cl;
		
		public ThreadOne(IUdpClient nCl, SocketAddress nDestAddr)
		{
			cl = nCl;
			setName("Thread One");
		}
		
		public void run()
		{
			Log._out(getName() + " Start");
			
			String msg = "Buna eu sunt One. tu cine esti?";
			Log._out(getName() + " sends: " + msg);
			cl.write(msg);
			msg = cl.read();
			Log._out(getName() + " received: " + msg);
			
			msg = "Ce mai faci?";
			Log._out(getName() + " sends: " + msg);
			cl.write(msg);
			msg = cl.read();
			Log._out(getName() + " received: " + msg);
			
			msg = "Cati ani ai?";
			Log._out(getName() + " sends: " + msg);
			cl.write(msg);
			msg = cl.read();
			Log._out(getName() + " received: " + msg);
			
			
			Log._out(getName() + " Stop");
		}
	}
	
	
	public static class ThreadTwo extends Thread
	{
		private SocketAddress destAddr;
		private IUdpClient cl;
		
		public ThreadTwo(IUdpClient nCl, SocketAddress nDestAddr)
		{
			destAddr = nDestAddr;
			cl = nCl;
		}
		
		public void run()
		{
			Log._out("Thread Two Start");
			
			String msg = cl.read();
			Log._out("Thread Two received: " + msg);
			msg = "Buna eu sunt Two.";
			Log._out("Thread Two sends: " + msg);
			cl.write(msg, destAddr);
			
			msg = cl.read();
			Log._out("Thread Two received: " + msg);
			msg = "Fac bine, multumesc.";
			Log._out("Thread Two sends: " + msg);
			cl.write(msg, destAddr);
			
			msg = cl.read();
			Log._out("Thread Two received: " + msg);
			msg = "12.";
			Log._out("Thread Two sends: " + msg);
			cl.write(msg, destAddr);
			
			
			Log._out("Thread Two Stop");
		}
	}

}
