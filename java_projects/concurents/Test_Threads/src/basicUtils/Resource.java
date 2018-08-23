package basicUtils;

import basicAtomics.IResource;

public class Resource implements IResource
{
  public int counter;

  public Resource()
  {
    counter = 0;
  }

  public void increment()
  {
    counter = counter + 1;
  }
  
  public int getValue()
  {
    return counter;
  }
}
