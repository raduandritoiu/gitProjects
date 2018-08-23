package http;

import http.models.HttpMethod;
import http.models.HttpResource;

public interface IHttpRequestHandler
{
  HttpResource getResource();
  HttpMethod getMethod();
  
  /**
   * At this point in time only the first line in request has been read and decoded
   */
  void handleStartSession(HttpRequestSession requestSession);
}
