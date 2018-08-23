package lib.integration.animals.models;

import lib.integration.common.interfaces.IModel;

public class Dog extends Animal implements IModel
{
  public Dog()
  {
    super("Scoop");
  }

  @Override
  public String toString()
  {
    String str = "I am the Dog "+name()+" of age " + age;
    if (!alive) str += ". (FUCK I'm dead - R.I.P.)";
    return str;
  }
}
