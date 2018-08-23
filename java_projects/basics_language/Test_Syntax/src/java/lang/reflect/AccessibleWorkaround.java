package java.lang.reflect;

public class AccessibleWorkaround
{
  public void setAccessible(AccessibleObject obj, boolean val)
  {
    obj.override = val;
  }
}
