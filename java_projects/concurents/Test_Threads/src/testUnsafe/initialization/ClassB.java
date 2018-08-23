package testUnsafe.initialization;

import basicUtils.Utils;

public class ClassB
{
  public int i1;
  public int i2 = 5;
  public int i3 = 6;
  public int i4;

  public ClassB()
  {
    Utils.Print("Class B is instantiated!");
    i1 = 101;
    i2 = 102;
  }
  
  public int sum()
  {
    return i1 + i2 + i3 + i4;
  }
}
