package http.auth;

public interface IUserManagerImpl
{
  public boolean open();
  public boolean close();
  public IUser getUser(String name);
}
