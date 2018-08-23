package http.models;

import java.util.HashMap;
import java.util.Iterator;

public class HttpMessage
{
  private final HashMap<String, String> headerFields;
  private final HashMap<String, HttpCookie> cookies;
  
  public int minor = 1, major = 1;
  public String body;

  public HttpMessage()
  {
    headerFields = new HashMap<>();
    cookies = new HashMap<>();
  }
  
  public void setField(String name, String value)
  {
    headerFields.put(name, value);
  }
  
  public String getField(String name)
  {
    return headerFields.get(name);
  }
  
  public void removeField(String name)
  {
    headerFields.remove(name);
  }
  
  public Iterator<String> fieldsIterator()
  {
    return headerFields.keySet().iterator();
  }
  
  
  public void setCookie(HttpCookie cookie)
  {
    cookies.put(cookie.name, cookie);
  }
  
  public HttpCookie getCookie(String name)
  {
    return cookies.get(name);
  }
  
  public void removeCookie(String name)
  {
    cookies.remove(name);
  }
  
  public Iterator<String> cookiesIterator()
  {
    return cookies.keySet().iterator();
  }
}
