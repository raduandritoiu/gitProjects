package language.override;

public class BaseClass
{
  public int i;
  
  public BaseClass(int ii) 
  {
    i = ii;
  }
  
  public int changeValue_prot()
  {
    return protectedChangeValue();
  }

  public int changeValue_priv()
  {
    return privateChangeValue();
  }

  private int privateChangeValue()
  {
    i = i * 2;
    return i;
  }
  
  protected int protectedChangeValue()
  {
    i = i * 2;
    return i;
  }

  public int wierdChangeValue()
  {
    i = i * 2;
    return i;
  }
}
