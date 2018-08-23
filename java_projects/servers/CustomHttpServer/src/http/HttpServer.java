package http;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashMap;

import tcp.server.ISocketHandler;
import tcp.server.TcpServer;


// NOT COMPLETE 

public class HttpServer
{
  private final HashMap<SocketAddress, ServerHandlerPair> requestHandlers;
  private final HttpSocketHandler standardHandler;
  
  public HttpServer()
  {
    requestHandlers = new HashMap<>();
    standardHandler = new HttpSocketHandler();
  }
  
  public HttpSocketHandler getStandardHandler()
  {
    return standardHandler;
  }
  
  public boolean stop()
  {
    
    return true;
  }
  
  // add socket
  public void addSocketHandler(String localAddr, int port)
  {
    addSocketHandler(new TcpServer(localAddr, port, 10), standardHandler);
  }
  public void addSocketHandler(InetAddress localAddr, int port)
  {
    addSocketHandler(new TcpServer(localAddr, port, 10), standardHandler);
  }
  public void addSocketHandler(InetSocketAddress localAddr)
  {
    addSocketHandler(new TcpServer(localAddr, 10), standardHandler);
  }
  public void addSocketHandler(String localAddr, int port, ISocketHandler handler)
  {
    addSocketHandler(new TcpServer(localAddr, port, 10), handler);
  }
  public void addSocketHandler(InetAddress localAddr, int port, ISocketHandler handler)
  {
    addSocketHandler(new TcpServer(localAddr, port, 10), handler);
  }
  public void addSocketHandler(InetSocketAddress localAddr, ISocketHandler handler)
  {
    addSocketHandler(new TcpServer(localAddr, 10), handler);
  }
  // main socket handler pairing function 
  public void addSocketHandler(TcpServer server, ISocketHandler handler)
  {
    requestHandlers.put(server.getLocalAddr(), new ServerHandlerPair(server, handler));
    server.setHandler(handler);
    server.start();
  }
  
  public TcpServer removeSocketHandler(String localAddr, int port)
  {
    return removeSocketHandler(new InetSocketAddress(localAddr, port));
  }
  public TcpServer removeSocketHandler(InetAddress localAddr, int port)
  {
    return removeSocketHandler(new InetSocketAddress(localAddr, port));
  }
  public TcpServer removeSocketHandler(SocketAddress localAddr)
  {
    ServerHandlerPair pair = requestHandlers.remove(localAddr);
    pair.server.setHandler(null);
    pair.server.stop();
    return pair.server;
  }
  
  public TcpServer getServer(String localAddr, int port)
  {
    return getServer(new InetSocketAddress(localAddr, port));
  }
  public TcpServer getServer(InetAddress localAddr, int port)
  {
    return getServer(new InetSocketAddress(localAddr, port));
  }
  public TcpServer getServer(SocketAddress localAddr)
  {
    ServerHandlerPair pair = requestHandlers.get(localAddr);
    if (pair != null) 
      return pair.server;
    return null;
  }
  
  public ISocketHandler getHandler(String localAddr, int port)
  {
    return getHandler(new InetSocketAddress(localAddr, port));
  }
  public ISocketHandler getHandler(InetAddress localAddr, int port)
  {
    return getHandler(new InetSocketAddress(localAddr, port));
  }
  public ISocketHandler getHandler(SocketAddress localAddr)
  {
    ServerHandlerPair pair = requestHandlers.get(localAddr);
    if (pair != null) 
      return pair.handler;
    return null;
  }
  
  private static class ServerHandlerPair
  {
    private final TcpServer server;
    private final ISocketHandler handler;
    
    private ServerHandlerPair(TcpServer nServer, ISocketHandler nHandler)
    {
      server = nServer;
      handler = nHandler;
    }
  }
}
