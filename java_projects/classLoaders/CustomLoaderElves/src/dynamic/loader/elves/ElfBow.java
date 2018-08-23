package dynamic.loader.elves;

import dynamic.loader.interfaces.IWeapon;


public class ElfBow implements IWeapon
{
  public String description()
  {
    return "ElfBow with " + damage() + " damage.";
  }
  
  public int damage()
  {
    return 50;
  }
  
  public void fire()
  {
    System.out.println("An arrow from the bow does " + damage() + " damage!");
  }
}
