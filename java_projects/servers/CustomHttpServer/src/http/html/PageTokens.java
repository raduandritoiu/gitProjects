package http.html;

import java.util.HashMap;
import java.util.Iterator;

public class PageTokens
{
  private HashMap<String, String> tokens;
  
  public PageTokens()
  {
    tokens = new HashMap<>();
  }
  
  public void setToken(String token, String value)
  {
    tokens.put(token, value);
  }
  
  public String getToken(String token)
  {
    return tokens.get(token);
  }
  
  public Iterator<String> keysIterator()
  {
    return tokens.keySet().iterator();
  }
  
}
