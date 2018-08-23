package dynamic.loader.barbarians;

import dynamic.loader.interfaces.IWeapon;


public class MisticalSword implements IWeapon
{
  public String description()
  {
    return "MisticalSword with " + damage() + " damage.";
  }
  
  public int damage()
  {
    return 230;
  }
  
  public void fire()
  {
    System.out.println("A thrust of the sword does " + damage() + " damage!");
  }
}
