package http.auth;

import java.net.InetAddress;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager
{
  private final ConcurrentHashMap<String, HttpSession> sessionsById;
  private final ConcurrentHashMap<String, HttpSession> sessionsByUser;

  
  public SessionManager()
  {
    sessionsById = new ConcurrentHashMap<>();
    sessionsByUser = new ConcurrentHashMap<>();
  }
  
  public HttpSession create(IUser user, InetAddress addr)
  {
    HttpSession session = new HttpSession(user, addr);
    sessionsById.put(session.id, session);
    sessionsByUser.put(user.getName(), session);
    return session;
  }
  
  public HttpSession findById(String sessionId)
  {
    if (sessionId == null)
      return null;
    return sessionsById.get(sessionId);
  }
  
  public HttpSession findByUser(String userName)
  {
    if (userName == null)
      return null;
    return sessionsByUser.get(userName);
  }
  
  public HttpSession checkById(String sessionId, InetAddress addr)
  {
    HttpSession session = sessionsById.get(sessionId);
    if (session == null)
      return null;
    if (session.addr.equals(addr))
      return session;
    return null;
  }
  
  public HttpSession checkByUser(String userName, InetAddress addr)
  {
    HttpSession session = sessionsByUser.get(userName);
    if (session == null)
      return null;
    if (session.addr.equals(addr))
      return session;
    return null;
  }
  
  public void remove(HttpSession session)
  {
    sessionsById.remove(session.id);
    sessionsByUser.remove(session.user.getName());
  }
  
  
  static String generateSessionID(IUser user, InetAddress addr)
  {
    Random rand = new Random();
    return user.getName()+"_" + rand.nextInt(100);
  }
}
