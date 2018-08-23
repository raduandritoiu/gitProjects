package lib.integration.cars.models;

import lib.integration.common.interfaces.IModel;

public class Volkswagen extends Car implements IModel
{
  public Volkswagen()
  {
    super("Volkswagen");
  }

  @Override
  public String toString()
  {
    return "Volkswagen Car has " + km + " km (damaged " + damage + "%)";
  }
}
