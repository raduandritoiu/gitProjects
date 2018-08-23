package tcp.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TcpClient
{
  public static Logger log = Logger.getLogger("tcp.client");
  
  public Socket sock;
  private SocketAddress localAddr;
  private SocketAddress remoteAddr;
  
  
  private PrintWriter out;
  private BufferedReader in;
  
  
  public TcpClient()
  {
    sock = new Socket();
    localAddr = sock.getLocalSocketAddress();
  }
  
  public TcpClient(String localAddrStr, int localPort)
  {
    try
    {
      sock = new Socket();
      sock.bind(new InetSocketAddress(localAddrStr, localPort));
      localAddr = sock.getLocalSocketAddress();
    }
    catch (Exception ex)
    {
      if (log.isLoggable(Level.FINE))
        log.fine("Error creating SOCKET: " + ex);
    }
  }
  
  
  public SocketAddress getLocalAddr()
  {
    return localAddr;
  }
  
  public SocketAddress getRemoteAddr()
  {
    return remoteAddr;
  }
  
  
  public boolean bind()
  {
    try
    {
      sock.bind(null);
      localAddr = sock.getLocalSocketAddress();
      return true;
    }
    catch (Exception ex)
    {
      if (log.isLoggable(Level.FINE))
        log.fine("Error binding SOCKET: " + ex);
      return false;
    }
  }
  
  public boolean bind(String localAddrStr, int localPort)
  {
    try
    {
      sock.bind(new InetSocketAddress(localAddrStr, localPort));
      localAddr = sock.getLocalSocketAddress();
      return true;
    }
    catch (Exception ex)
    {
      if (log.isLoggable(Level.FINE))
        log.fine("Error binding SOCKET: " + ex);
      return false;
    }
  }
  
  
  public boolean connect(String remoteAddrStr, int remotePort)
  {
    return connect(remoteAddrStr, remotePort, 0);
  }
  
  public boolean connect(String remoteAddrStr, int remotePort, int timeout)
  {
    try
    {
      sock.connect(new InetSocketAddress(remoteAddrStr, remotePort), timeout);
      remoteAddr = sock.getRemoteSocketAddress();
      out = new PrintWriter(sock.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
      return true;
    }
    catch (Exception ex)
    {
      if (log.isLoggable(Level.INFO))
        log.info("Error connecting SOCKET: " + ex);
      return false;
    }
  }
 
  
  public boolean write(String msg)
  {
    out.println(msg);
    return true;
  }
  
  
  public String read()
  {
    try
    {
      String str = in.readLine();
      return str;
    }
    catch (Exception ex)
    {
      if (log.isLoggable(Level.INFO))
        log.info("Error reading SOCKET: " + ex);
    }
    return null;
  }
}
