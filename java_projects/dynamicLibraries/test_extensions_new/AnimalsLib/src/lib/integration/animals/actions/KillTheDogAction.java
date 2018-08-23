package lib.integration.animals.actions;

import lib.integration.animals.models.Dog;
import lib.integration.common.interfaces.IAction;

public class KillTheDogAction implements IAction
{
  private final String _name;
  public Dog dog;
  
  
  public KillTheDogAction(Dog n_dog)
  {
    _name = "FLIPP_THE_DOG";
    dog = n_dog;
  }
  
  @Override
  public String name()
  {
    return _name;
  }

  
  public int execute()
  {
    dog.alive = !dog.alive;
    return 0;
  }

  @Override
  public String toString()
  {
    return "Action that FLIPPS the dog <"+dog.name()+">";
  }
}
