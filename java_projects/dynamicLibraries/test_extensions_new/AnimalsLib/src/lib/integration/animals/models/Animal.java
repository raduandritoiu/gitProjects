package lib.integration.animals.models;

public class Animal
{
  private final String _name;
  public boolean alive;
  public int age;

  public Animal(String n_name)
  {
    _name = n_name;
    age = 1;
    alive = true;
  }
  
  public String name()
  {
    return _name;
  }
}
