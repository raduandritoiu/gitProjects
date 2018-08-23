  package http.auth;

import java.net.InetAddress;

public class HttpSession
{
  public final String id;
  public final InetAddress addr;
  public final IUser user;
  public String extraInfo;
  
  public HttpSession(IUser nUser, InetAddress nAddr)
  {
    id = SessionManager.generateSessionID(nUser, nAddr);
    user = nUser;
    addr = nAddr;
  }
}
