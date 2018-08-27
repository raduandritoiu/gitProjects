package testing;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import radua.servers.client.interfaces.IUdpClient;

public class TwoThreadsTesting 
{
	public static void run() throws Exception
	{
		SocketAddress addr1 = new InetSocketAddress("192.168.100.3", 3333);
		SocketAddress addr2 = new InetSocketAddress("192.168.100.3", 4444);
		
		TestUdpConnect cl1 = new TestUdpConnect(addr1);
		cl1.connect(addr2);
		
		TestUdpStandard cl2 = new TestUdpStandard(addr2);
		
		Thread one = new ThreadOne(cl1, addr2);
		Thread two = new ThreadTwo(cl2, addr1);
		
		
		two.start();
		one.start();
		
		one.join();
		two.join();
		
	}
	
	
	
	public static class ThreadOne extends Thread
	{
		private SocketAddress destAddr;
		private IUdpClient cl;
		
		public ThreadOne(IUdpClient nCl, SocketAddress nDestAddr)
		{
			destAddr = nDestAddr;
			cl = nCl;
		}
		
		public void run()
		{
			System.out.println("Thread One Start");
			
			String msg = "Buna eu sunt One. tu cine esti?";
			System.out.println("Thread One sends: " + msg);
			cl.write(msg);
			msg = cl.read();
			System.out.println("Thread One received: " + msg);
			
			msg = "Ce mai faci?";
			System.out.println("Thread One sends: " + msg);
			cl.write(msg);
			msg = cl.read();
			System.out.println("Thread One received: " + msg);
			
			msg = "Cati ani ai?";
			System.out.println("Thread One sends: " + msg);
			cl.write(msg);
			msg = cl.read();
			System.out.println("Thread One received: " + msg);
			
			
			System.out.println("Thread One Stop");
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
			System.out.println("Thread Two Start");
			
			String msg = cl.read();
			System.out.println("Thread Two received: " + msg);
			msg = "Buna eu sunt Two.";
			System.out.println("Thread Two sends: " + msg);
			cl.write(msg, destAddr);
			
			msg = cl.read();
			System.out.println("Thread Two received: " + msg);
			msg = "Fac bine, multumesc.";
			System.out.println("Thread Two sends: " + msg);
			cl.write(msg, destAddr);
			
			msg = cl.read();
			System.out.println("Thread Two received: " + msg);
			msg = "12.";
			System.out.println("Thread Two sends: " + msg);
			cl.write(msg, destAddr);
			
			
			System.out.println("Thread Two Stop");
		}
	}

}
