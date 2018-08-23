import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import http.HttpSocketHandler;
import tcp.server.TcpServer;

public class MainTcpServer
{
  public static void main(String[] args)
  {
    Logger log = Logger.getLogger("tcp");
    log.setLevel(Level.ALL);
    log.addHandler(new ConsoleHandler());
    
    
    // set up server 
    TcpServer server = new TcpServer("192.168.1.38", 44444, 5);
    HttpSocketHandler handler2 = new HttpSocketHandler();
    server.setHandler(handler2);
    
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
