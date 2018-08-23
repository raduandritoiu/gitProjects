package testUnsafe.initialization;

import basicUtils.Utils;

public class ClassD
{
  public final int i1;
  public final int i2 = 5;
  public int i3 = 6;
  public int i4;

  public ClassD()
  {
    Utils.Print("Class D is instantiated!");
    i1 = 101;
  }
  
  public int sum()
  {
    return i1 + i2 + i3 + i4;
  }
}
