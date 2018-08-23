package http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import http.models.HttpMethod;
import http.models.HttpRequest;
import http.models.HttpResource;
import http.utils.HttpDecoder;
import tcp.server.ISocketHandler;
import tcp.server.TcpServer;


public class HttpSocketHandler implements ISocketHandler
{
  public static final Logger log = TcpServer.log;
  
  // OBS: index them first by method because that can be a key
  //      resource name can't be a key because one resource accepts all subresources
  private final HashMap<HttpMethod, ArrayList<IHttpRequestHandler>> requestHandlers;
  
  
  public HttpSocketHandler()
  {
    requestHandlers = new HashMap<>();
  }
  
  
  public String getName()
  {
    return "HTTP Standard Socket Handler";
  }
  
  
  public synchronized void addResourceHandler(IHttpRequestHandler handler)
  {
    // TODO - maybe synchronize in 2 parts
    ArrayList<IHttpRequestHandler> handlers;
    handlers = requestHandlers.get(handler.getMethod());
    if (handlers == null)
    {
      handlers = new ArrayList<>();
      requestHandlers.put(handler.getMethod(), handlers);
    }
    handlers.add(handler);
  }
  
  
  public synchronized void removeResourceHandler(IHttpRequestHandler handler)
  {
    ArrayList<IHttpRequestHandler> handlers = requestHandlers.get(handler.getMethod());
    if (handlers == null)
      return;
    int idx = handlers.indexOf(handler);
    if (idx == -1)
      return;
    if (handlers.size() == 1)
    {
      requestHandlers.remove(handler.getMethod());
      return;
    }
    handlers.remove(idx);
  }
  

  public synchronized void removeResourceHandler(HttpResource resource, HttpMethod method)
  {
    ArrayList<IHttpRequestHandler> handlers = requestHandlers.get(method);
    if (handlers == null)
      return;
    for (int i = 0; i < handlers.size(); i++)
    {
      if (handlers.get(i).getResource().accepts(resource))
      {
        if (handlers.size() == 1)
        {
          requestHandlers.remove(method);
          return;
        }
        handlers.remove(i);
        return;
      }
    }
  }
  
  
  public IHttpRequestHandler findHandler(HttpRequest req)
  {
    if (req == null)
      return null;
    
    ArrayList<IHttpRequestHandler> handlers = requestHandlers.get(req.method);
    if (handlers == null)
      return null;
    
    IHttpRequestHandler handler = null;
    for (int i = 0; i < handlers.size(); i++)
    {
      IHttpRequestHandler tmp = handlers.get(i);
      if (tmp.getResource().accepts(req.resource))
      {
        handler = tmp;
        break;
      }
    }
    
    return handler;
  }
  
  
  public void handleSocket(Socket sock)
  {
    String sockAddrStr = sock.getRemoteSocketAddress().toString();
    String localAddrStr = sock.getLocalSocketAddress().toString();
    System.out.println("*** Read from socket <"+sockAddrStr+"> - locally handled by <"+localAddrStr+">");
    
    //first parse just the HHTP request line
    BufferedReader in = null;
    HttpRequest req = new HttpRequest();
    try
    {
      in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
      HttpDecoder.parseRequestLine(req, in);
    }
    catch (Exception ex)
    {
      req = null;
      System.out.println("*** *** Request parse error from socket "+sockAddrStr+"  "+ex+" : "+ex.getMessage());
      if (log.isLoggable(Level.WARNING))
        log.warning(Thread.currentThread().getName() + " - Error handling SOCKET" + sockAddrStr + " : " + ex + "\n");
    }
    
    System.out.println("*** *** Request <"+req+">  from socket <"+sockAddrStr+">");
    
    // if no req - however could that be - close socket
    if (req == null)
    {
      try
      {
        System.out.println("*** *** NO request from socket <"+sockAddrStr+">");
        sock.close();
      }
      catch (Exception ex)
      {
        if (log.isLoggable(Level.INFO))
          log.info("Error closing SOCKET: " + ex);
      }
      return;
    }
    
    // find the handler
    IHttpRequestHandler handler = findHandler(req);
    if (handler == null)
    {
      try
      {
        System.out.println("*** *** *** NO handler for socket <"+sockAddrStr+">  and request <"+req+">");
        sock.close();
      }
      catch (Exception ex)
      {
        if (log.isLoggable(Level.INFO))
          log.info("Error closing SOCKET: " + ex);
      }
      return;
    }

    // handle the request 
    System.out.println("*** *** *** Handler <" + handler.toString()+">  found for socket <"+sockAddrStr+">  and request <"+req+">");
    HttpRequestSession session = new HttpRequestSession(sock, in, req);
    handler.handleStartSession(session);
    
    // clean up
    if (!sock.isClosed())
      try { sock.close(); }
      catch (Exception ex) {}
  }
}
