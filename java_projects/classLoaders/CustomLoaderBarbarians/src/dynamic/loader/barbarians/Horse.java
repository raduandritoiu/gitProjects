package dynamic.loader.barbarians;

import dynamic.loader.interfaces.IPet;


public class Horse implements IPet
{
  public String description()
  {
    return "Horse runs and attaks with " + damage() + " damage.";
  }
  
  public int damage()
  {
    return 40;
  }
  
  public void attak()
  {
    System.out.println("Horse strikes with " + damage() + " damage!");
  }
  
  public void run()
  {
    System.out.println("Horse is running!");
  }
}
