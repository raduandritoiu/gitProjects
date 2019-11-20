package dynamic.loader.elves;

import dynamic.loader.interfaces.IPet;

public class AirDragon implements IPet
{
  public String description()
  {
    return "AirDragon flyes and attaks with " + damage() + " damage.";
  }
  
  public int damage()
  {
    return 280;
  }
  
  public void attak()
  {
    System.out.println("AirDragon shoots fire with " + damage() + " damage!");
  }
  
  public void run()
  {
    System.out.println("AirDragon is flying!");
  }
}
