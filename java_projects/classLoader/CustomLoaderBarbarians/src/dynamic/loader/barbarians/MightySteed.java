package dynamic.loader.barbarians;

import dynamic.loader.interfaces.IPet;


public class MightySteed implements IPet
{
  public String description()
  {
    return "MightySteed gallops and attaks with " + damage() + " damage.";
  }
  
  public int damage()
  {
    return 120;
  }
  
  public void attak()
  {
    System.out.println("Horse bites and hooves with " + damage() + " damage!");
  }
  
  public void run()
  {
    System.out.println("MightySteed is galloping!");
  }
}
