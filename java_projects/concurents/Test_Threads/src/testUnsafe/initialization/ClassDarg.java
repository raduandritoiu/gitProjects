package testUnsafe.initialization;

import basicUtils.Utils;

public class ClassDarg
{
  public final int i1;
  public final int i2 = 5;
  public int i3 = 6;
  public int i4;

  public ClassDarg(int arg)
  {
    Utils.Print("Class D is instantiated!");
    i1 = 101;
    i4 = arg;
  }
  
  public int sum()
  {
    return i1 + i2 + i3 + i4;
  }
}
