package testing.https;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import http.auth.AuthManager;
import http.auth.HttpSession;
import http.html.PageGenerator;
import http.models.HttpCookie;
import http.models.HttpRequest;
import http.models.HttpResponse;
import http.models.HttpStatus;
import http.utils.HttpDecoder;
import http.utils.HttpEncoder;
import tcp.server.ISocketHandler;
import testing.html.GlobalVariables;

public class MockSocketHandler
    implements ISocketHandler
{

  @Override
  public String getName()
  {
    return "bla_bla";
  }

  @Override
  public void handleSocket(Socket sock)
  {
    try
    { 
      internalHandleSocket(sock); 
    }
    catch (Exception ex)
    {
      System.out.println("Fix PULA!");
    }
  }
  
  private void internalHandleSocket(Socket sock) throws Exception
  {
    String sockAddrStr = sock.getRemoteSocketAddress().toString();
    String localAddrStr = sock.getLocalSocketAddress().toString();
    System.out.println("*** Read from socket <"+sockAddrStr+"> - locally handled by <"+localAddrStr+">");
    
    //first parse just the HHTP request line
    HttpRequest req = new HttpRequest();
    BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
    
    // decode the request
    int ch = 0;
    while ((ch = in.read()) != -1)
    {
      System.out.print((char) ch);
    }
    
    
//    HttpDecoder.parseRequestLine(req, in);
//    HttpDecoder.parseHeader(req, in);
//    HttpDecoder.parseCookies(req);
//    HttpDecoder.parseBody(req, in);
    
    System.out.println("*** *** Request <"+req+">  from socket <"+sockAddrStr+">");
    
    // try to find if active session
    HttpSession httpSession = null;
    HttpCookie cookie = req.getCookie("sessionToken");
    if (cookie != null)
      httpSession = AuthManager.get().sessions.checkById(cookie.value, sock.getInetAddress());

    
    
    // create the reply
    HttpResponse response = new HttpResponse();
    response.status = HttpStatus.S_200;
    response.setField("Date", "Wed, 19 Oct 2016 14:16:47 GMT");
    response.setField("Server", "Apache/2.4.23 (Win32) OpenSSL/1.0.2h PHP/7.0.9");
    response.setField("Keep-Alive", "timeout=5, max=100");
    response.setField("Connection", "Keep-Alive");
    response.setField("Content-Type", "text/html");
    response.setField("Accept-Ranges", "bytes");
    response.body = PageGenerator.getPage(GlobalVariables.pagePath, "welcome.html", null);
    response.setField("Content-Length", "" + response.body.length());
    
    // endcode the reply
    String sendStr = HttpEncoder.encodeSome(response);
    
    // send the reply
    PrintWriter out = new PrintWriter(sock.getOutputStream(), false);
    out.print(sendStr);
    out.flush();
    
    System.out.println("*** *** *** *** Sending HTTP response for socket <"+sockAddrStr+">   req <"+req+">");
    sock.close();
  }
}
