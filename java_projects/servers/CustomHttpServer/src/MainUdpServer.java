import java.net.InetSocketAddress;

import udp.server.ISessionHandler;
import udp.server.UdpServer;

public class MainUdpServer
{
  public static void main(String[] args)
  {
    ISessionHandler handler = new TestUdpSessionHandler();
    UdpServer server = new UdpServer(handler, "10.10.13.136", 44444, 5);
    System.out.println("Server listens on " + server.getLocalAddr());
    
    server.start();
    
    try { Thread.sleep(1000 * 10); }
    catch(Exception ex){}

    
    
    server.write("fuck you!!!", new InetSocketAddress("10.10.15.255", 44444));
    
    
    boolean running = true;
    int pace = 0;
    while (running)
    {
      pace++;
    }
    
    server.stopWait();
    System.out.println("Server CLOSED!");
  }
}
