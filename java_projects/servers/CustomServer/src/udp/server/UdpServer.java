package udp.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UdpServer implements IUdpCommunicator
{
  public static Logger log = Logger.getLogger("udp.server");
  
  private ISessionHandler sessionHandler;
  private SocketAddress localAddr;
  private DatagramSocket mainSock;
  
  private ConcurrentHashMap<SocketAddress, SessionKey> sessions;
  private AcceptingThread mainTh;
  private ExecutorService pool;
  private boolean isRunning;
  private int numThreads;
  
  
  public UdpServer(ISessionHandler nHandler, String nLocalAddr, int nLocalPort, int numTh)
  {
    numThreads = numTh;
    try
    {
      mainSock = new DatagramSocket(new InetSocketAddress(nLocalAddr, nLocalPort));
      localAddr = mainSock.getLocalSocketAddress();
      sessionHandler = nHandler;
    }
    catch (Exception ex)
    {
      if (log.isLoggable(Level.SEVERE))
        log.severe("Error creating SOCKET: " + ex);
    }
  }
  
  
  public UdpServer getServer()
  {
    return this;
  }
  
  
  public SocketAddress getLocalAddr()
  {
    return localAddr;
  }
  
  
  public void start()
  {
    isRunning = true;
    sessions = new ConcurrentHashMap<>();
    pool = Executors.newFixedThreadPool(numThreads);
    mainTh = new AcceptingThread();
    mainTh.start();
  }
  
  
  public void stop()
  {
    mainSock.close();
    isRunning = false;
    pool.shutdown();
  }
  
  
  public void stopWait()
  {
    stop();
    
    try
    {
      mainTh.join();
    }
    catch (Exception ex)
    {
      if (log.isLoggable(Level.WARNING))
        log.warning("Error waiting Main Thread: " + ex);
    }
    
    try
    {
      pool.awaitTermination(2, TimeUnit.MINUTES);
    }
    catch (Exception ex)
    {
      if (log.isLoggable(Level.WARNING))
        log.warning("Error waiting POOL: " + ex);
    }
  }
  
  
  public String read(SocketAddress remoteAddr)
  {
    SessionKey newKey = new SessionKey();
    SessionKey key = sessions.putIfAbsent(remoteAddr, newKey);
    if (key == null) key = newKey;
    
    synchronized (key)
    {
      try
      {
        if (key.bytes == null)
        {
          key.wait();
        }
      }
      catch(Exception ex)
      {
        if (log.isLoggable(Level.INFO))
          log.info("Error reading from SOCKET: " + ex);
      }
    }
    
    sessions.remove(remoteAddr);
    String str = new String(key.bytes);
    return str;
  }
  
  
  public void write(String msg, SocketAddress remoteAddr)
  {
    DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.getBytes().length, remoteAddr);
    try
    {
      mainSock.send(packet);
    }
    catch (Exception ex)
    {
      if (log.isLoggable(Level.SEVERE))
        log.severe("Error sending SOCKET: " + ex);
    }
  }
  
  
//==========================================================================
//==========================================================================
  
  class AcceptingThread extends Thread
  {
   public AcceptingThread()
   {}
   
   public void run()
   {
     setName("AcceptingThread");
     while(isRunning)
     {
       // read
       byte[] buf = new byte[100];
       DatagramPacket packet = new DatagramPacket(buf, buf.length);
       try
       {
         mainSock.receive(packet);
       }
       catch (Exception ex)
       {
         if (log.isLoggable(Level.SEVERE))
           log.severe("Error reading SOCKET: " + ex);
         continue;
       }

       // find existing session
       SocketAddress remoteAddr = packet.getSocketAddress();
       SessionKey sessionKey = sessions.get(remoteAddr);
       if (sessionKey != null)
       {
         synchronized(sessionKey)
         {
           sessionKey.bytes = buf;
           sessionKey.notifyAll();
         }
         continue;
       }
       
       // create new session
       UdpSession newSession = new UdpSession(remoteAddr, getServer(), buf);
       pool.execute(new SessionRunnable(newSession));
     }
   }
  }
  
//==========================================================================
  
  private class SessionRunnable implements Runnable
  {
    private UdpSession session;
    public SessionRunnable(UdpSession nSession)
    {
      session = nSession;
    }
    
    public void run()
    {
      sessionHandler.handleSession(session);
    }
  }
}