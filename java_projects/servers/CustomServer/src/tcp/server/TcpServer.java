package tcp.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TcpServer
{
  public static Logger log = Logger.getLogger("tcp.server");
  
  private final SocketAddress localAddr;
  private final ServerSocket mainSock;
  
  private ISocketHandler socketHandler;
  
  private AcceptingThread mainTh;
  private ExecutorService pool;
  private boolean isRunning;
  private int numThreads;
  
  
  public TcpServer(InetSocketAddress nLocalAddr, int numTh)
  {
    this(nLocalAddr, null, null, 0, numTh);
  }
  
  public TcpServer(InetAddress nLocalAddr, int nLocalPort, int numTh)
  {
    this(null, nLocalAddr, null, nLocalPort, numTh);
  }
  
  public TcpServer(String nLocalAddr, int nLocalPort, int numTh)
  {
    this(null, null, nLocalAddr, nLocalPort, numTh);
  }
  
  private TcpServer(InetSocketAddress nSockAddr, InetAddress nInetAddr, String nStrAddr, int nPort, int numTh)
  {
    if (nSockAddr != null)
    {
      nInetAddr = nSockAddr.getAddress();
      nPort = nSockAddr.getPort();
    }
    else if (nInetAddr == null && nStrAddr != null) 
    {
      try 
      {
        nInetAddr = InetAddress.getByName(nStrAddr);
      }
      catch (Exception ex) {}
    }
    
    ServerSocket tmpSock = null;
    SocketAddress tmpAddr = null;
    numThreads = numTh;
    try
    {
      tmpSock = new ServerSocket(nPort, 20, nInetAddr);
      tmpAddr = tmpSock.getLocalSocketAddress();
      System.out.println("TCP Server listens on " + tmpAddr);
    }
    catch (Exception ex)
    {
      if (log.isLoggable(Level.SEVERE))
        log.severe("Error creating SOCKET: " + ex);
    }
    localAddr = tmpAddr;
    mainSock = tmpSock;
  }
  
  
  public SocketAddress getLocalAddr()
  {
    return localAddr;
  }
  
  
  public void setHandler(ISocketHandler nSocketHandler)
  {
    socketHandler = nSocketHandler;
  }
  
  
  public void start()
  {
    if (isRunning)
      return;
    isRunning = true;
    pool = Executors.newFixedThreadPool(numThreads);
    mainTh = new AcceptingThread();
    mainTh.start();
  }
  
  
  public void stop()
  {
    if (!isRunning)
      return;
    try
    {
      mainSock.close();
    }
    catch(IOException ex)
    {
      if (log.isLoggable(Level.SEVERE))
        log.severe("Error stopping SOCKET: " + ex);
    }
    isRunning = false;
    pool.shutdown();
  }
  
  
  public boolean stopWait()
  {
    stop();
    
    int tries = 0;
    while (tries < 5 && mainTh.isAlive())
    {
      tries ++;
      try
      {
        mainTh.join();
      }
      catch(InterruptedException ex)
      {
        if (log.isLoggable(Level.WARNING))
          log.warning("Error waiting THREAD: " + ex);
      }
    }
    
    tries = 0;
    boolean waiting = true;
    while (waiting && tries < 5 && !pool.isTerminated())
    {
      tries ++;
      try
      {
        waiting = !pool.awaitTermination(2, TimeUnit.MINUTES);
      }
      catch(InterruptedException ex)
      {
        if (log.isLoggable(Level.WARNING))
          log.warning("Error waiting POOL: " + ex);
      }
    }
    
    return pool.isTerminated() && !mainTh.isAlive();
  }
  
  
//==========================================================================
//==========================================================================
  
  
  private class AcceptingThread extends Thread
  {
    public AcceptingThread()
    {}
    
    public void run()
    {
      setName("AcceptingThread");
      while (isRunning)
      {
        try
        {
          Socket sock = mainSock.accept();
          System.out.println("*** Accepted socket from " + sock.getRemoteSocketAddress().toString());
          if (isRunning)
          {
            pool.execute(new SessionRunnable(sock));
          }
          else
          {
            sock.close();
          }
        }
        catch (Exception ex)
        {
          if (log.isLoggable(Level.WARNING))
            log.warning("Error accepting SOCKET: " + ex);
        }
      }
    }
  }
  
//==========================================================================
  
  private class SessionRunnable implements Runnable
  {
    private Socket sock;
    public SessionRunnable(Socket nSock)
    {
      sock = nSock;
    }
    
    public void run()
    {
      socketHandler.handleSocket(sock);
    }
  }
}
