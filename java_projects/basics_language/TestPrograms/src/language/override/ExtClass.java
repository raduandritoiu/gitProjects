package language.override;

public class ExtClass extends BaseClass 
{
  public ExtClass(int ii)
  {
    super(ii);
  }
  
  // NU pot suprascrie si creste nivelul de vizivilitate al unei functii, de la private la public
  // deoarece NU pot suprascrie o functie private ca NU o vad
  public int privateChangeValue()
  {
    i = i - 10;
    return i;
  }

  // pot suprascrie si creste nivelul de vizivilitate al unei functii, de la protected la public
  @Override
  public int protectedChangeValue()
  {
    i = i - 10;
    return i;
  }
  
  @Override
  public int wierdChangeValue()
  {
    i = i - 10;
    return i;
  }
}
