package lib.integration.common.basicImpls;

import lib.integration.common.interfaces.IAction;

public class BasicAction implements IAction
{
  private final String _name;
  public int val;

  public BasicAction(String n_name, int n_val)
  {
    _name = n_name;
    val = n_val;
  }
  
  @Override
  public String name()
  {
    return _name;
  }

  @Override
  public int execute()
  {
    System.out.println("*** Action <"+_name+"> is executing with response: " + val);
    return val;
  }
  
  @Override
  public String toString()
  {
    return "*** Common Action <"+_name+"> is allways executing with response: " + val;
  }
}
