package http.html;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;

public class PageGenerator
{
  public static String getPage(String pagePath, String pageName, PageTokens tokens) throws Exception
  {
    String page = "";
    String line = "";
    BufferedReader reader = new BufferedReader(new FileReader(pagePath + pageName));
    while ((line = reader.readLine()) != null)
    {
      if (tokens != null)
        line = replaceTokens(line, tokens);
      page += line;
    }
    reader.close();
    return page;
  }
  
  public static String replaceTokens(String line, PageTokens tokens)
  {
    Iterator<String> it = tokens.keysIterator();
    while (it.hasNext())
    {
      String token = it.next();
      line = line.replaceAll("\\{\\$__"+token+"\\}", tokens.getToken(token));
    }
    return line;
  }
}
