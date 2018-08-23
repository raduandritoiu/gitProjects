package lib.integration.animals.models;

import lib.integration.common.interfaces.IModel;

public class Cat extends Animal implements IModel
{
  public Cat(String n_name)
  {
    super(n_name);
  }
  
  @Override
  public String toString()
  {
    String str = "I am the Cat "+name()+" of age " + age;
    if (!alive) str += ". (FUCK I'm dead - R.I.P.)";
    return str;
  }
}
