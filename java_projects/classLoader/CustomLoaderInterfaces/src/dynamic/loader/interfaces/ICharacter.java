package dynamic.loader.interfaces;

public interface ICharacter extends IDescription
{
  void init();
  
  void setWeapon(IWeapon weapon);
  IWeapon getWeapon();
  
  void setPet(IPet pet);
  IPet getPet();
}
