package testUnsafe.initialization;

import basicUtils.Utils;

public class ClassA
{
  private int i1;
  private int i2 = 5;
  private int i3 = 6;
  private int i4;

  public ClassA()
  {
    Utils.Print("Class A is instantiated!");
    i1 = 101;
    i2 = 102;
  }
  
  public int getI1()
  {
    return i1;
  }
  
  public int getI2()
  {
    return i2;
  }
  
  public int getI3()
  {
    return i3;
  }
  
  public int getI4()
  {
    return i4;
  }
  
  public int sum()
  {
    return i1 + i2 + i3 + i4;
  }
}
