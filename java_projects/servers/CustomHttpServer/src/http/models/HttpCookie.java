package http.models;

import java.util.Date;

public class HttpCookie
{
  public final String name;
  public final String value;
  public final Date expDate;
  public final int expAge;
  
  public HttpCookie(String nName, String nValue)
  {
    name = nName;
    value = nValue;
    expDate = null;
    expAge = -1;
  }
  
  public HttpCookie(String nName, String nValue, int nExpAge)
  {
    name = nName;
    value = nValue;
    expDate = null;
    expAge = nExpAge;
  }
  
  public HttpCookie(String nName, String nValue, Date nExpDate)
  {
    name = nName;
    value = nValue;
    expDate = nExpDate;
    expAge = -1;
  }
}
