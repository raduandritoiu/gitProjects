package testDispatch.collision;

public class CollisionObject
{
  private String _name;
  
  public CollisionObject(String n)
  {
    _name = "Objects";
  }

  public String name()
  {
    return _name;
  }
  
  public String collideWith(CollisionObject obj)
  {
    return name() + " colided with " + obj.name();
  }
}
