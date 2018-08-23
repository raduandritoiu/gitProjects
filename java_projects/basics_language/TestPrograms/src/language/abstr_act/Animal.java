package language.abstr_act;

public abstract class Animal
{
  public int x, y;
  
  public abstract String speek();
  
  public void move(int a, int b)
  {
    x += a;
    y += b;
  }
}
