package testing.html.requests;

import http.HttpRequestSession;
import http.auth.AuthManager;
import http.auth.HttpSession;
import http.html.PageGenerator;
import http.html.PageTokens;
import http.models.HttpCookie;
import http.models.HttpMethod;
import http.models.HttpResource;
import http.models.HttpResponse;
import http.models.HttpStatus;
import http.requests.RequestHandlerStub;
import testing.business.data.models.User;
import testing.html.GlobalVariables;


public class TestPost extends RequestHandlerStub
{
  private final HttpResource resource = new HttpResource("/carsOnline/");
  
  public HttpResource getResource()
  {
    return resource;
  }

  public HttpMethod getMethod()
  {
    return HttpMethod.POST;
  }

  protected HttpResponse handleRequestSession(HttpRequestSession requestSession) throws Exception
  {
    HttpResponse resp = new HttpResponse();
    handleGeneral(requestSession, resp);
    String localResource = requestSession.req.resource.toBaseResource(resource);
    
    if (localResource.equals("loginDone") || localResource.equals("loginDone.html"))
      return handleLoginDone(requestSession, resp);
    else
      return handleUnknown(requestSession, resp);
  }
  
  private HttpResponse handleGeneral(HttpRequestSession requestSession, HttpResponse response)
  {
    response.status = HttpStatus.S_200;
    response.setField("Date", "Wed, 19 Oct 2016 14:16:47 GMT");
    response.setField("Server", "Apache/2.4.23 (Win32) OpenSSL/1.0.2h PHP/7.0.9");
    response.setField("Keep-Alive", "timeout=5, max=100");
    response.setField("Connection", "Keep-Alive");
    response.setField("Content-Type", "text/html");
    response.setField("Accept-Ranges", "bytes");
    return response;
  }
  
  private HttpResponse handleLoginDone(HttpRequestSession requestSession, HttpResponse response) throws Exception
  {
    String name = requestSession.req.postParams.containsKey("uname") ? requestSession.req.postParams.get("uname") : "";
    String pass = requestSession.req.postParams.containsKey("pname") ? requestSession.req.postParams.get("pname") : "";
    User user = (User) AuthManager.get().users.check(name, pass);
    boolean ok = user != null;
    
    PageTokens tokens = new PageTokens();
    tokens.setToken("title", ok ? "Works" : "Error");
    tokens.setToken("hello", ok ? "Hello <b> "+user.name+" </b>. Nice to see you again." : "We are sorry Mr. <b>"+name+"</b>.");
    tokens.setToken("greeting", ok ? "Congratulations for your <b>"+user.age+" years </b>." : "We do not know you.");
    
    if (ok)
    {
      // check for already session 
      HttpSession session = AuthManager.get().sessions.checkByUser(user.name, requestSession.sock.getInetAddress());
      if (session != null)
      {
        tokens.setToken("successPage", "player");
        tokens.setToken("successText", "Go to your page!");
      }
      else
      {
        tokens.setToken("successPage", "playerStart");
        tokens.setToken("successText", "First set up info!");
        // create new session
        session = AuthManager.get().sessions.create(user, requestSession.sock.getInetAddress());
      }
      
      response.setCookie(new HttpCookie("sessionToken", session.id, 30000));
      response.setCookie(new HttpCookie("readMyCookie", "isn_t_this_fun", 30000));
    }
    else
    {
      tokens.setToken("successPage", "welcome");
      tokens.setToken("successText", "Go back!");
    }
    
    response.body = PageGenerator.getPage(GlobalVariables.pagePath, "loginDone.html", tokens);
    return response;
  }
  
  private HttpResponse handleUnknown(HttpRequestSession requestSession, HttpResponse response) throws Exception
  {
    response.body = PageGenerator.getPage(GlobalVariables.pagePath, "unknown.html", null);
    return response;
  }
  
  public String toString()
  {
    return getMethod().name() + "  " + resource;
  }
}
