package language.abstr_act;

public abstract class FormaGeom
{
  public int h;
  public int n;
  
  public FormaGeom()
  {}
  
  abstract void draw();
  abstract int area();
}
