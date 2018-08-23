package testing.business.data.models;

import http.auth.IUser;

public class User implements IUser
{
  public final String name;
  public final String pass;
  public final int age;
  public final int money;
  public Address addr;
  public Kingdom kingdom;

  public User(String nName, String nPass, int nAge, int nMoney)
  {
    this(nName, nPass, nAge, nMoney, null, null);
  }
  
  public User(String nName, String nPass, int nAge, int nMoney, Address nAddr, Kingdom nKingdom)
  {
    name = nName;
    pass = nPass;
    age = nAge;
    money = nMoney;
    addr = nAddr;
    kingdom = nKingdom;
  }
  
  public String getName() 
  {
    return name;
  }
  
  public String getPass()
  {
    return pass;
  }
}
