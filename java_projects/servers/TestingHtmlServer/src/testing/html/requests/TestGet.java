package testing.html.requests;

import http.HttpRequestSession;
import http.auth.AuthManager;
import http.html.PageGenerator;
import http.html.PageTokens;
import http.models.HttpMethod;
import http.models.HttpResource;
import http.models.HttpResponse;
import http.models.HttpStatus;
import http.requests.RequestHandlerStub;
import testing.business.data.models.User;
import testing.html.GlobalVariables;


public class TestGet extends RequestHandlerStub
{
  private final HttpResource resource = new HttpResource("/carsOnline/");
  
  public HttpResource getResource()
  {
    return resource;
  }

  public HttpMethod getMethod()
  {
    return HttpMethod.GET;
  }

  protected HttpResponse handleRequestSession(HttpRequestSession requestSession) throws Exception
  {
    HttpResponse response = new HttpResponse();
    generateGeneralRequest(requestSession, response);
    String localResource = requestSession.req.resource.toBaseResource(resource);
    
    // test the player first
    if (localResource.equals("player") || localResource.equals("player.html"))
      return handlePlayer(requestSession, response);
    else if (localResource.equals("logout") || localResource.equals("logout.html"))
      return handleLogout(requestSession, response);

    // test if already logged in
    if (requestSession.httpSession != null)
    {
      if (requestSession.httpSession.extraInfo != null)
        return handlePlayer(requestSession, response);
      else
        return handlePlayerStart(requestSession, response);
    }
    
    // if not follow the normal path
    if (localResource.equals("welcome") || localResource.equals("welcome.html"))
      return handleWelcome(requestSession, response);
    else if (localResource.equals("login") || localResource.equals("login.html"))
      return handleLoginShow(requestSession, response);
    else if (localResource.equals("playerStart") || localResource.equals("playerStart.html"))
      return handlePlayerStart(requestSession, response);
    else
      return handleUnknown(requestSession, response);
  }
  
  
  private HttpResponse generateGeneralRequest(HttpRequestSession requestSession, HttpResponse response) throws Exception
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

  private HttpResponse handleWelcome(HttpRequestSession requestSession, HttpResponse response) throws Exception
  {
    response.body = PageGenerator.getPage(GlobalVariables.pagePath, "welcome.html", null);
    return response;
  }

  private HttpResponse handleLoginShow(HttpRequestSession requestSession, HttpResponse response) throws Exception
  {
    response.body = PageGenerator.getPage(GlobalVariables.pagePath, "login.html", null);
    return response;
  }
  
  private HttpResponse handlePlayerStart(HttpRequestSession requestSession, HttpResponse response) throws Exception
  {
    PageTokens tokens = new PageTokens();
    tokens.setToken("user", requestSession.httpSession.user.getName());
    tokens.setToken("sessionId", requestSession.httpSession.id);
    response.body = PageGenerator.getPage(GlobalVariables.pagePath, "playerStart.html", tokens);
    return response;
  }
  
  private HttpResponse handlePlayer(HttpRequestSession requestSession, HttpResponse response) throws Exception
  {
    // update session info
    String info = requestSession.req.urlParams.get("session_info");
    if (info != null)
      requestSession.httpSession.extraInfo = info;
    
    PageTokens tokens = new PageTokens();
    User user = (User) requestSession.httpSession.user;
    tokens.setToken("user", user.name);
    tokens.setToken("age", user.age + "");
    tokens.setToken("money", user.money + "");
    tokens.setToken("sessionId", requestSession.httpSession.id);
    tokens.setToken("sessionInfo", requestSession.httpSession.extraInfo);
    response.body = PageGenerator.getPage(GlobalVariables.pagePath, "player.html", tokens);
    return response;
  }
  
  private HttpResponse handleLogout(HttpRequestSession requestSession, HttpResponse response) throws Exception
  {
    // update session info
    PageTokens tokens = new PageTokens();
    AuthManager.get().sessions.remove(requestSession.httpSession);
    tokens.setToken("user", requestSession.httpSession.user.getName());
    response.body = PageGenerator.getPage(GlobalVariables.pagePath, "logoutDone.html", tokens);
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
