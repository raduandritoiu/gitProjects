package http.models;

public enum HttpStatus
{
  S_100(100, "Continue"),
  S_101(101, "Switching Protocols"),
  S_200(200, "OK"),
  S_201(201, "Created"),
  S_202(202, "Accepted"),
  S_203(203, "Non-Authoritative Information"),
  S_204(204, "No Content"),
  S_205(205, "6: Reset Content"),
  S_206(206, "7: Partial Content"),
  S_300(300, "Multiple Choices"),
  S_301(301, "Moved Permanently"),
  S_302(302, "Found"),
  S_303(303, "See Other"),
  S_304(304, "Not Modified"),
  S_305(305, "6: Use Proxy"),
  S_307(307, "8: Temporary Redirect"),
  S_400(400, "Bad Request"),
  S_401(401, "Unauthorized"),
  S_402(402, "Payment Required"),
  S_403(403, "Forbidden"),
  S_404(404, "Not Found"),
  S_405(405, "Method Not Allowed"),
  S_406(406, "Not Acceptable"),
  S_407(407, "Proxy Authentication Required"),
  S_408(408, "Request Time-out"),
  S_409(409, "Conflict"),
  S_410(410, "Gone"),
  S_411(411, "Length Required"),
  S_412(412, "Precondition Failed"),
  S_413(413, "Request Entity Too Large"),
  S_414(414, "Request-URI Too Large"),
  S_415(415, "Unsupported Media Type"),
  S_416(416, "Requested range not satisfiable"),
  S_417(417, "Expectation Failed"),
  S_500(500, "Internal Server Error"),
  S_501(501, "Not Implemented"),
  S_502(502, "Bad Gateway"),
  S_503(503, "Service Unavailable"),
  S_504(504, "Gateway Time-out"),
  S_505(505, "HTTP Version not supported");
  
  public final int code;
  public String phrase;
  
  private HttpStatus(int nCode, String nPhrase)
  {
    code = nCode;
    phrase = nPhrase;
  }
  
  public String toString()
  {
    return code + " " + phrase;
  }
}
