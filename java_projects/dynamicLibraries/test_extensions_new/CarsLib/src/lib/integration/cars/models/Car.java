package lib.integration.cars.models;

public class Car
{
  private final String _name;
  public int damage;
  public int km;
  
  public Car(String n_name)
  {
    _name = n_name;
    damage = 0;
    km = 1;
  }
  
  public String name()
  {
    return _name;
  }
}
