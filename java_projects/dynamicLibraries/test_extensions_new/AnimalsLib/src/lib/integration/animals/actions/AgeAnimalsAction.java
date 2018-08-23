package lib.integration.animals.actions;

import java.util.ArrayList;
import java.util.Iterator;

import lib.integration.animals.models.Animal;
import lib.integration.common.interfaces.IAction;

public class AgeAnimalsAction implements IAction
{
  private final String _name;
  public final ArrayList<Animal> animals;
  public int ageInc;
  
  
  public AgeAnimalsAction(int n_age)
  {
    _name = "AGE_ANIMALS_ACTION";
    ageInc = n_age;
    animals = new ArrayList<>();
  }
  
  @Override
  public String name()
  {
    return _name;
  }

  
  public int execute()
  {
    Iterator<Animal> iter = animals.iterator();
    while (iter.hasNext())
    {
      Animal animal = iter.next();
      animal.age += ageInc;
    }
    return 0;
  }

  @Override
  public String toString()
  {
    return "Action that increases animals age with " + ageInc;
  }
}
