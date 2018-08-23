package http.utils;

import java.util.Iterator;

import http.models.HttpCookie;
import http.models.HttpResponse;

public class HttpEncoder
{
  public static String encodeSome(HttpResponse resp)
  {
    String respEnc = "";
    
    // status line
    respEnc = "HTTP/" + resp.major + "." + resp.minor + " " + resp.status + "\n";
    
    // header fields
    Iterator<String> it = resp.fieldsIterator();
    while (it.hasNext())
    {
      String key = it.next();
      respEnc += key + ": " + resp.getField(key)+ "\n";
    }
    
    // cookies
    it = resp.cookiesIterator();
    while (it.hasNext())
    {
      String cookieName = it.next();
      HttpCookie cookie = resp.getCookie(cookieName);
      respEnc += "Set-Cookie: "+cookie.name+"="+cookie.value+"; Max-Age="+cookie.expAge+"\n";
    }
    respEnc += "\n";
    
    
    // body
    respEnc += resp.body;
    
    return respEnc;
  }
}
