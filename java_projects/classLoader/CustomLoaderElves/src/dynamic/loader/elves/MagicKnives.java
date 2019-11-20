package dynamic.loader.elves;

import dynamic.loader.interfaces.IWeapon;

public class MagicKnives implements IWeapon
{
  public String description()
  {
    return "MagicKnives with " + damage() + " damage.";
  }
  
  public int damage()
  {
    return 180;
  }
  
  public void fire()
  {
    System.out.println("A cut from the knife does " + damage() + " damage!");
  }
}
