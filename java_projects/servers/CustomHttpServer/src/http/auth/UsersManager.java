package http.auth;

import java.util.concurrent.ConcurrentHashMap;

public class UsersManager
{
  private final ConcurrentHashMap<String, IUser> usersCash;
  private IUserManagerImpl managerImpl;
  
  
  public UsersManager(IUserManagerImpl nManagerImpl)
  {
    usersCash = new ConcurrentHashMap<>();
    setImpl(nManagerImpl);
  }
  
  public void setImpl(IUserManagerImpl nManagerImpl)
  {
    if (managerImpl != null)
      managerImpl.close();
    managerImpl = nManagerImpl;
    if (managerImpl != null)
      managerImpl.open();
  }
  
  public void add(IUser user)
  {
    usersCash.put(user.getName(), user);
  }
  
  public IUser find(String name)
  {
    if (name == null)
      return null;
    IUser user = usersCash.get(name);
    if (managerImpl != null && user == null)
    {
      user = managerImpl.getUser(name);
      if (user != null)
        usersCash.put(name, user);
    }
    return user;
  }
  
  public IUser check(String name, String pass)
  {
    IUser user = find(name);
    if (user == null)
      return null;
    if (user.getPass().equals(pass))
      return user;
    return null;
  }
}
