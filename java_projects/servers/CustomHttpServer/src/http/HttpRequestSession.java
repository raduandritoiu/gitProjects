package http;

import java.io.BufferedReader;
import java.net.Socket;

import http.auth.HttpSession;
import http.models.HttpRequest;

public class HttpRequestSession
{
  public Socket sock;
  public BufferedReader in;
  public HttpRequest req;
  public HttpSession httpSession;
  
  public HttpRequestSession(Socket nSock, BufferedReader nIn, HttpRequest nInit)
  {
    sock = nSock;
    in = nIn;
    req = nInit;
  }
}
