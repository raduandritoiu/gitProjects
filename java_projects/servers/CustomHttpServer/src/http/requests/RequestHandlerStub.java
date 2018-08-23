package http.requests;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;

import http.HttpRequestSession;
import http.HttpSocketHandler;
import http.IHttpRequestHandler;
import http.auth.AuthManager;
import http.models.HttpCookie;
import http.models.HttpMethod;
import http.models.HttpRequest;
import http.models.HttpResource;
import http.models.HttpResponse;
import http.utils.HttpDecoder;
import http.utils.HttpEncoder;

public class RequestHandlerStub implements IHttpRequestHandler
{
  @Override
  public void handleStartSession(HttpRequestSession requestSession)
  {
    String sockAddrStr = requestSession.sock.getRemoteSocketAddress().toString();
    
    try 
    {
      System.out.println("*** *** *** Handling HTTP from socket <"+sockAddrStr+">  req <"+requestSession.req+">");
      
      HttpRequest req = requestSession.req;
      BufferedReader in = requestSession.in;
      
      // decode the request
      HttpDecoder.parseHeader(req, in);
      HttpDecoder.parseCookies(req);
      HttpDecoder.parseBody(req, in);
      
      // try to find if active session
      HttpCookie cookie = req.getCookie("sessionToken");
      if (cookie != null)
        requestSession.httpSession = AuthManager.get().sessions.checkById(cookie.value, requestSession.sock.getInetAddress());

      // create the reply
      HttpResponse resp = handleRequestSession(requestSession);
      resp.setField("Content-Length", "" + resp.body.length());
      
      // endcode the reply
      String sendStr = HttpEncoder.encodeSome(resp);
      
      // send the reply
      Socket sock = requestSession.sock;
      PrintWriter out = new PrintWriter(sock.getOutputStream(), false);
      out.print(sendStr);
      out.flush();
      
      System.out.println("*** *** *** *** Sending HTTP response for socket <"+sockAddrStr+">   req <"+requestSession.req+">");
      sock.close();
    }
    catch (Exception ex)
    {
      if (HttpSocketHandler.log.isLoggable(Level.WARNING))
        HttpSocketHandler.log.warning(Thread.currentThread().getName() + " - Error handling SOCKET " + sockAddrStr + " : " + ex + "\n");
    }
  }
  
  
  // -------- need to be overridden
  @Override
  public HttpResource getResource()
  {
    return null;
  }

  @Override
  public HttpMethod getMethod()
  {
    return null;
  }
  
  protected HttpResponse handleRequestSession(HttpRequestSession requestSession) throws Exception
  {
    return null;
  }
}
