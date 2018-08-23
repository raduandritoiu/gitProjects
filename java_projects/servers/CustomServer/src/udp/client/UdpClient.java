package udp.client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UdpClient
{
  public static Logger log = Logger.getLogger("udp.client");

  
  private DatagramSocket sock;
  private SocketAddress localAddr;
  private SocketAddress remoteAddr;
  
  
  public UdpClient()
  {
    try
    {
      sock = new DatagramSocket();
      localAddr = sock.getLocalSocketAddress();
    }
    catch (Exception ex)
    {
      if (log.isLoggable(Level.SEVERE))
        log.severe("Error creating SOCKET: " + ex);
    }
  }
  
  public UdpClient(String nLocalAddr, int nLocalPort)
  {
    try
    {
      sock = new DatagramSocket(new InetSocketAddress(nLocalAddr, nLocalPort));
      localAddr = sock.getLocalSocketAddress();
    }
    catch (Exception ex)
    {
      if (log.isLoggable(Level.SEVERE))
        log.severe("Error creating SOCKET: " + ex);
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
  
  
  public void connect(String nRemoteAddr, int nRemotePort)
  {
    try
    {
      sock.connect(new InetSocketAddress(nRemoteAddr, nRemotePort));
      localAddr = sock.getLocalSocketAddress();
      remoteAddr = sock.getRemoteSocketAddress();
    }
    catch (Exception ex)
    {
      if (log.isLoggable(Level.SEVERE))
        log.severe("Error connecting SOCKET: " + ex);
    }
  }
  
  
  public String read()
  {
    byte[] buf = new byte[100];
    DatagramPacket packet = new DatagramPacket(buf, buf.length);
    try
    {
      sock.receive(packet);
      String str = new String(packet.getData());
      return str;
    }
    catch (Exception ex)
    {
      if (log.isLoggable(Level.SEVERE))
        log.severe("Error reading SOCKET: " + ex);
    }
    return null;
  }
  
  
  public void write(String msg)
  {
    DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.getBytes().length);
    try
    {
      sock.send(packet);
    }
    catch (Exception ex)
    {
      if (log.isLoggable(Level.SEVERE))
        log.severe("Error sending SOCKET: " + ex);
    }
  }
}
