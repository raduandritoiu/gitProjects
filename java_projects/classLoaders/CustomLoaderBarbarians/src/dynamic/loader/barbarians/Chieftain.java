package dynamic.loader.barbarians;

import dynamic.loader.interfaces.ICharacter;
import dynamic.loader.interfaces.IPet;
import dynamic.loader.interfaces.IWeapon;

public class Chieftain implements ICharacter
{
  private IWeapon _weapon;
  private IPet _pet;
  
  public String description()
  {
    return "Chieftain has:" + "\n - weapon: " + _weapon.description() + "\n - pet: " + _pet.description();
  }
  
  public void init()
  {
    _weapon = new MisticalSword();
    _pet = new MightySteed();
  }
  
  public void setWeapon(IWeapon weapon)
  {
    _weapon = weapon;
  }
  public IWeapon getWeapon()
  {
    return _weapon;
  }
  
  public void setPet(IPet pet)
  {
    _pet = pet;
  }
  public IPet getPet()
  {
    return _pet;
  }
}
