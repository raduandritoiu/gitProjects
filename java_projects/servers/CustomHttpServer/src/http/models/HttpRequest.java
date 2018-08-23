package http.models;

import java.util.HashMap;

public class HttpRequest extends HttpMessage
{
  public HttpMethod method;
  public HttpResource resource;
  public HashMap<String, String> urlParams;
  public HashMap<String, String> postParams;
  
  public HttpRequest()
  {
    urlParams = new HashMap<>();
    postParams = new HashMap<>();
  }
  
  public String toString()
  {
    return method.name() + "  " + resource.toString();
  }
}
