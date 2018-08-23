package testTemplate;

public class Thing<T>
{
  protected int val;
  
  public Thing()
  {
    val = -1;
  }
  
  public void setThing(int v)
  {
    val = v;
  }
  
  public int getVal()
  {
    return val;
  }
  
  public T myClone()
  {
    return (T) this;
  }
}
