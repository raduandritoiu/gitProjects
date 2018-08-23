package lib.integration.cars.models;

import lib.integration.common.interfaces.IModel;

public class BMW extends Car implements IModel
{
  public BMW(int series)
  {
    super("BMW M"+series+" Series");
  }

  @Override
  public String toString()
  {
    return name() + " Car has " + km +" km (damaged " + damage + "%)";
  }
}
