package http.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

import http.models.HttpCookie;
import http.models.HttpMessage;
import http.models.HttpMethod;
import http.models.HttpRequest;
import http.models.HttpResource;

public class HttpDecoder
{
  public static void parseRequestLine(HttpRequest req, BufferedReader in) throws IOException
  {
    StringTokenizer lineTok = new StringTokenizer(in.readLine(), " ");
    req.method = HttpMethod.valueOf(lineTok.nextToken().toUpperCase());
    
    String token = lineTok.nextToken();
    int idx = token.indexOf('?');
    if (idx == -1)
    {
      req.resource = new HttpResource(token);
    }
    else
    {
      req.resource = new HttpResource(token.substring(0, idx));
      StringTokenizer paramTok = new StringTokenizer(token.substring(idx + 1), "&");
      while (paramTok.hasMoreTokens())
      {
        token = paramTok.nextToken();
        idx = token.indexOf('=');
        if (idx != -1)
        {
          req.urlParams.put(token.substring(0, idx), token.substring(idx+1));
        }
      }
    }
    
    token = lineTok.nextToken();
    req.major = token.charAt(5) - 48;
    req.minor = token.charAt(7) - 48;
  }
  
  
  public static void parseHeader(HttpMessage msg, BufferedReader in) throws IOException
  {
    String line = in.readLine();
    while (line != null && !line.equals(""))
    {
      int idx = line.indexOf(':');
      if (idx == -1)
        continue;
      msg.setField(line.substring(0, idx).trim(), line.substring(idx+1).trim());
      line = in.readLine();
    }
  }
  
  
  public static void parseCookies(HttpMessage msg) throws IOException
  {
    String cookiesVal = msg.getField("Cookie");
    if (cookiesVal == null)
      return;
    msg.removeField("Cookie");
    StringTokenizer cookieTok = new StringTokenizer(cookiesVal, ";");
    while (cookieTok.hasMoreTokens())
    {
      String crtCookie = cookieTok.nextToken().trim();
      int idx = crtCookie.indexOf('=');
      if (idx != -1)
      {
        HttpCookie cookie = new HttpCookie(crtCookie.substring(0, idx), crtCookie.substring(idx+1));
        msg.setCookie(cookie);
      }
    }
  }
  
  
  public static void parseBody(HttpRequest req, BufferedReader in)
  {
    int len = -1;
    if (req.getField("Content-Length") != null)
      len = Integer.parseInt(req.getField("Content-Length"));
    
    if (len >= 0)
    {
      try
      {
        char[] buff = new char[len];
        in.read(buff, 0, len);
        String line = new String(buff);
        StringTokenizer lineTok = new StringTokenizer(line, "&");
        while (lineTok.hasMoreTokens())
        {
          StringTokenizer paramTok = new StringTokenizer(lineTok.nextToken(), "=");
          req.postParams.put(paramTok.nextToken(), paramTok.nextToken());
        }
      }
      catch (Exception ex)
      {}
    }
  }
}
