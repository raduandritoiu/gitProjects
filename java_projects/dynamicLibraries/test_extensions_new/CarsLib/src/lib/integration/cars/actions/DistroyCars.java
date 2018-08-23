package lib.integration.cars.actions;

import java.util.ArrayList;
import java.util.Iterator;

import lib.integration.cars.models.Car;
import lib.integration.common.interfaces.IAction;


public class DistroyCars implements IAction
{
  private final String _name;
  public final int destroyPercent;
  public final ArrayList<Car> cars;
  
  public DistroyCars(int percent)
  {
    _name = "AGE_ANIMALS_ACTION";
    destroyPercent = percent;
    cars = new ArrayList<>();
  }

  
  @Override
  public String name()
  {
    return _name;
  }

  
  public int execute()
  {
    Iterator<Car> iter = cars.iterator();
    while (iter.hasNext())
    {
      Car car = iter.next();
      car.damage -= destroyPercent;
    }
    return 0;
  }

  
  @Override
  public String toString()
  {
    return "Action that DESTROYS cars by "+destroyPercent+"% percentage";
  }
}
