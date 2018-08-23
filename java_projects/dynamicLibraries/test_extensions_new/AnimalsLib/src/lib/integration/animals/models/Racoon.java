package lib.integration.animals.models;

import lib.integration.common.interfaces.IModel;


public class Racoon extends Animal implements IModel
{
  public Racoon()
  {
    super("Rocket");
  }
  
  @Override
  public String toString()
  {
    String str = "I am the Racoon "+name()+" of age " + age;
    if (!alive) str += ". (FUCK I'm dead - R.I.P.)";
    return str;
  }
}
