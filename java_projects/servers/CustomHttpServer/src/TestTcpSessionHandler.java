

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import tcp.server.ISocketHandler;
import tcp.server.TcpServer;

public class TestTcpSessionHandler implements ISocketHandler
{
  private static Logger log = TcpServer.log;
  
  
  public String getName()
  {
    return "SOCK_TEST";
  }


  public void handleSocket(Socket sock)
  {
    String recvStr;
    String sendStr;
    SocketAddress remoteAddr = sock.getRemoteSocketAddress();
    
    try 
    {
      PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
      
      recvStr = in.readLine();
      int ws = (new Random().nextInt(10)) * (new Random().nextInt(10));
      try { Thread.sleep(ws * 10); }
      catch(Exception ex){}
      sendStr = "Hello. I am the Server.";
      out.println(sendStr);

      recvStr = in.readLine();
      ws = (new Random().nextInt(10)) * (new Random().nextInt(10));
      try { Thread.sleep(ws * 10); }
      catch(Exception ex){}
      sendStr = "" + ((char)0) + ((char)24) + "The phrase is MUMULET!";
      out.println(sendStr);
      
      recvStr = in.readLine();
      ws = (new Random().nextInt(10)) * (new Random().nextInt(10));
      try { Thread.sleep(ws * 10); }
      catch(Exception ex){}
      sendStr = "Any time. Bye bye.";
      out.println(sendStr);
      
      sock.close();
      
      System.out.println("*** Client from : " + remoteAddr);
    }
    catch (Exception ex)
    {
      if (log.isLoggable(Level.WARNING))
        log.warning(Thread.currentThread().getName() + " - Error handling SOCKET: " + ex + "\n");
    }
  }
}
