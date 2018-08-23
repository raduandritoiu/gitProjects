package dynamic.loader.barbarians;

import dynamic.loader.interfaces.IWeapon;


public class BattleAxe implements IWeapon
{
  public String description()
  {
    return "BattleAxe with " + damage() + " damage.";
  }
  
  public int damage()
  {
    return 30;
  }
  
  public void fire()
  {
    System.out.println("A swing of the axe does " + damage() + " damage!");
  }
}
