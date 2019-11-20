package udpComm;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;


public class UdpCommunication
{
  public static void main(String[] args) throws Exception
  {
    String[] in_args = args;
    in_args = new String[1];
    in_args[0] = "all";
    
    RunArgs runArgs = null;
    
    // ==== help =====
    if (in_args.length > 0 && in_args[0].equals("help"))
    { 
      runArgs = new RunArgs();
      System.out.println("Needs at least one argument: udpComm <cnt> <bind_addr> <dest_addr>" +
          "\n\t <cnt> - number of msgs to send/recv " +
          "\n\t       - optional, default value: 1" +
          "\n\t <bind_addr> - optional, default value: "+runArgs.bindAddr+":"+runArgs.bindPort+":"+runArgs.bindReuse +
          "\n\t <dest_addr> - optional, default value is to recvs msgs");
      return;
    }
    
    // ==== run all tests ====
    else if (in_args.length > 0 && in_args[0].equals("all"))
    {
      runArgs = parseArgs(in_args, 1);
      runAllWithArgs(runArgs);
    }
    
    // ==== run just one test ====
    else
    {
      runArgs = parseArgs(in_args, 0);
      runWithArgs(runArgs);
    }
  }
  
  
  public static RunArgs parseArgs(String[] args, int idx) throws Exception
  {
    // ==== hard code args =====
    RunArgs runArgs = new RunArgs(4, "192.168.100.2", 55001, 1, true, "192.168.100.3", 55001);
    
    // ==== parse arguments =====
    if (args.length > idx+0)
    {
      runArgs.cnt = Integer.parseInt(args[idx+0]);
    }
    if (args.length > idx+1)
    {
      int pos1 = args[idx+1].indexOf(":");
      int pos2 = args[idx+1].lastIndexOf(":");
      runArgs.bindAddr = args[idx+1].substring(0, pos1);
      runArgs.bindPort = Integer.parseInt(args[idx+1].substring(pos1+1, pos2));
      runArgs.bindReuse = Integer.parseInt(args[idx+1].substring(pos2+1));
    }
    if (args.length > idx+2)
    {
      runArgs.send = true;
      int pos = args[idx+2].indexOf(":");
      runArgs.sendAddr = args[idx+2].substring(0, pos);
      runArgs.sendPort = Integer.parseInt(args[idx+2].substring(pos + 1));
    }
    
    return runArgs;
  }
  
  
  public static void runAllWithArgs(RunArgs a) throws Exception
  {
//    runWildcard(a.cnt, a.bindAddr,  a.bindPort, 0, a.send, a.bindAddr,  a.bindPort, 0, a.sendAddr, a.sendPort);
//    runWildcard(a.cnt, "0.0.0.0",   a.bindPort, 0, a.send, a.bindAddr,  a.bindPort, 0, a.sendAddr, a.sendPort);
//    
//    runWildcard(a.cnt, a.bindAddr,  a.bindPort, 0, a.send, "0.0.0.0",   a.bindPort, 0, a.sendAddr, a.sendPort);
//    runWildcard(a.cnt, "0.0.0.0",   a.bindPort, 0, a.send, "0.0.0.0",   a.bindPort, 0, a.sendAddr, a.sendPort);
//    
//    runWildcard(a.cnt, a.bindAddr,  a.bindPort, 0, a.send, a.bindAddr,  a.bindPort, 0, "192.168.100.255", a.sendPort);
//    runWildcard(a.cnt, "0.0.0.0",   a.bindPort, 0, a.send, a.bindAddr,  a.bindPort, 0, "192.168.100.255", a.sendPort);
//    
    runWildcard(a.cnt, a.bindAddr,  a.bindPort, 0, a.send, "0.0.0.0",   a.bindPort, 0, "192.168.100.255", a.sendPort);
    runWildcard(a.cnt, "0.0.0.0",   a.bindPort, 0, a.send, "0.0.0.0",   a.bindPort, 0, "192.168.100.255", a.sendPort);
    //
//    runWildcard(a.cnt, a.bindAddr,  a.bindPort, 0, a.send, a.bindAddr,  a.bindPort, 1, a.sendAddr, a.sendPort);
//    runWildcard(a.cnt, "0.0.0.0",   a.bindPort, 0, a.send, a.bindAddr,  a.bindPort, 1, a.sendAddr, a.sendPort);
//    
//    runWildcard(a.cnt, a.bindAddr,  a.bindPort, 0, a.send, "0.0.0.0",   a.bindPort, 1, a.sendAddr, a.sendPort);
//    runWildcard(a.cnt, "0.0.0.0",   a.bindPort, 0, a.send, "0.0.0.0",   a.bindPort, 1, a.sendAddr, a.sendPort);
//    
//    runWildcard(a.cnt, a.bindAddr,  a.bindPort, 0, a.send, a.bindAddr,  a.bindPort, 1, "192.168.100.255", a.sendPort);
//    runWildcard(a.cnt, "0.0.0.0",   a.bindPort, 0, a.send, a.bindAddr,  a.bindPort, 1, "192.168.100.255", a.sendPort);
//    
    runWildcard(a.cnt, a.bindAddr,  a.bindPort, 0, a.send, "0.0.0.0",   a.bindPort, 1, "192.168.100.255", a.sendPort);
    runWildcard(a.cnt, "0.0.0.0",   a.bindPort, 0, a.send, "0.0.0.0",   a.bindPort, 1, "192.168.100.255", a.sendPort);
    
    
//    runWildcard(a.cnt, a.bindAddr,  a.bindPort, 1, a.send, a.bindAddr,  a.bindPort, 0, a.sendAddr, a.sendPort);
//    runWildcard(a.cnt, "0.0.0.0",   a.bindPort, 1, a.send, a.bindAddr,  a.bindPort, 0, a.sendAddr, a.sendPort);
//    
//    runWildcard(a.cnt, a.bindAddr,  a.bindPort, 1, a.send, "0.0.0.0",   a.bindPort, 0, a.sendAddr, a.sendPort);
//    runWildcard(a.cnt, "0.0.0.0",   a.bindPort, 1, a.send, "0.0.0.0",   a.bindPort, 0, a.sendAddr, a.sendPort);
//    
//    runWildcard(a.cnt, a.bindAddr,  a.bindPort, 1, a.send, a.bindAddr,  a.bindPort, 0, "192.168.100.255", a.sendPort);
//    runWildcard(a.cnt, "0.0.0.0",   a.bindPort, 1, a.send, a.bindAddr,  a.bindPort, 0, "192.168.100.255", a.sendPort);
    
    runWildcard(a.cnt, a.bindAddr,  a.bindPort, 1, a.send, "0.0.0.0",   a.bindPort, 0, "192.168.100.255", a.sendPort);
    runWildcard(a.cnt, "0.0.0.0",   a.bindPort, 1, a.send, "0.0.0.0",   a.bindPort, 0, "192.168.100.255", a.sendPort);
    //
//    runWildcard(a.cnt, a.bindAddr,  a.bindPort, 1, a.send, a.bindAddr,  a.bindPort, 1, a.sendAddr, a.sendPort);
//    runWildcard(a.cnt, "0.0.0.0",   a.bindPort, 1, a.send, a.bindAddr,  a.bindPort, 1, a.sendAddr, a.sendPort);
//    
//    runWildcard(a.cnt, a.bindAddr,  a.bindPort, 1, a.send, "0.0.0.0",   a.bindPort, 1, a.sendAddr, a.sendPort);
//    runWildcard(a.cnt, "0.0.0.0",   a.bindPort, 1, a.send, "0.0.0.0",   a.bindPort, 1, a.sendAddr, a.sendPort);
//    
//    runWildcard(a.cnt, a.bindAddr,  a.bindPort, 1, a.send, a.bindAddr,  a.bindPort, 1, "192.168.100.255", a.sendPort);
//    runWildcard(a.cnt, "0.0.0.0",   a.bindPort, 1, a.send, a.bindAddr,  a.bindPort, 1, "192.168.100.255", a.sendPort);
    
    runWildcard(a.cnt, a.bindAddr,  a.bindPort, 1, a.send, "0.0.0.0",   a.bindPort, 1, "192.168.100.255", a.sendPort);
    runWildcard(a.cnt, "0.0.0.0",   a.bindPort, 1, a.send, "0.0.0.0",   a.bindPort, 1, "192.168.100.255", a.sendPort);
  }
  
  
  public static void runWildcard(int cnt, String recvBindAddr, int recvBindPort, int recvBindReuse, boolean send,  String sendBindAddr, int sendBindPort, int sendBindReuse, String sendAddr, int sendPort) throws Exception
  {
    if (send)
      runWithArgs(new RunArgs(cnt, sendBindAddr, sendBindPort, sendBindReuse, send, sendAddr, sendPort));
    else
      runWithArgs(new RunArgs(cnt, recvBindAddr, recvBindPort, recvBindReuse, send, sendAddr, sendPort));
  }
  
  
  public static void runWithArgs(RunArgs runArgs) throws Exception
  {
    System.out.println("--- " + runArgs);
    
    // ==== bind =====
    DatagramSocket socket = new DatagramSocket(null);
    if (runArgs.bindReuse == 0)
    {
      socket.bind(new InetSocketAddress(runArgs.bindAddr, runArgs.bindPort));
      System.out.println("Program (simple) binds to: "+runArgs.bindAddr+":"+runArgs.bindPort);
    }
    else
    {
      socket.setBroadcast(true);
      socket.setReuseAddress(true);
      socket.bind(new InetSocketAddress(runArgs.bindAddr, runArgs.bindPort));
      System.out.println("Program (useReuse) binds to: "+runArgs.bindAddr+":"+runArgs.bindPort);
    }

    byte[] data = new byte[] {0, 12, 18, 36, 41, 54};
    
    // ==== send message =====
    if (runArgs.send)
    {
      System.out.println("Program sends ("+runArgs.cnt+") packet to: " + runArgs.sendAddr);
      for (int i = 0; i < runArgs.cnt; i++)
      {
        DatagramPacket packet = new DatagramPacket(data, data.length, new InetSocketAddress(runArgs.sendAddr, runArgs.sendPort));
        socket.send(packet);
      }
      System.out.println("Program DONE sending ("+runArgs.cnt+") packet to: " + runArgs.sendAddr);
    }
    // ==== wait for message =====
    else
    {
      System.out.println("Program waits for ("+runArgs.cnt+") packet.");
      int recv_cnt = 0;
      DatagramPacket packet = null;
      while (recv_cnt < runArgs.cnt)
      {
        byte[] recv = new byte[data.length];
        packet = new DatagramPacket(recv, recv.length);
        socket.receive(packet);
        
        recv_cnt++;
        boolean eq = true;
        for (int i = 0; i < recv.length; i++)
          eq = eq && (recv[i] == data[i]);
        
        System.out.println("Received ("+recv_cnt+") from "+packet.getAddress()+":"+packet.getPort()+" data = "+bytesToString(recv)+" - <"+eq+">");
      }
      System.out.println("Program DONE receiving ("+runArgs.cnt+") packets from "+packet.getAddress()+":"+packet.getPort());
    }
    
    // ==== end =====
    socket.close();
    System.out.println("Program DONE!!! --------- \n\n");
  }
  
  
  private static String bytesToString(byte[] arr)
  {
    if (arr == null)
      return "null";
    String str = "";
    for (int i = 0; i< arr.length; i++)
      str = str + arr[i] + ", ";
    return str;
  }
}