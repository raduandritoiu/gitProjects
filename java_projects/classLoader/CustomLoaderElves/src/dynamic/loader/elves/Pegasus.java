package dynamic.loader.elves;

import dynamic.loader.interfaces.IPet;


public class Pegasus implements IPet
{
  public String description()
  {
    return "Pegasus glides and attaks with " + damage() + " damage.";
  }
  
  public int damage()
  {
    return 70;
  }
  
  public void attak()
  {
    System.out.println("Pegasus hits with " + damage() + " damage!");
  }
  
  public void run()
  {
    System.out.println("Pegasus is gliding!");
  }
}
