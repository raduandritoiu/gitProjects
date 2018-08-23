package lib.integration.common.basicImpls;

import lib.integration.common.interfaces.IModel;

public class BasicModel implements IModel
{
  private final String _name;
  public int val;

  public BasicModel(String n_name, int n_val)
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
  public String toString()
  {
    return "Common Model <"+_name+"> with val <"+val+">";
  }
}
