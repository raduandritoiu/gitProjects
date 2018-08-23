package http.models;

public enum HttpMethod
{
  GET(2),
  POST(2),
  PUT(4),
  DELETE(16);
  
  public int code;
  
  private HttpMethod(int nCode)
  {
    code = nCode;
  }
}
