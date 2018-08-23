package http.auth;

public class AuthManager
{
  public final UsersManager users;
  public final SessionManager sessions;
  
  
  // ==== singleton stuff ===== 
  private AuthManager()
  {
    users = new UsersManager(null);
    sessions = new SessionManager();
  }
  public static AuthManager _auth;
  public static AuthManager get()
  {
    if (_auth != null) 
      return _auth;
    _auth = new AuthManager();
    return _auth;
  }
}
