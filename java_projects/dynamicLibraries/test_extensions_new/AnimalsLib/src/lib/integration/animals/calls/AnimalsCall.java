package lib.integration.animals.calls;

import lib.integration.common.interfaces.ICall;

public class AnimalsCall implements ICall
{
  private final String _name;
  private final int _sessionId;
  
  public AnimalsCall(int n_sessionId)
  {
    _name = "WE_ARE_THE_ANIMALS";
    _sessionId = n_sessionId;
  }
  
  @Override
  public String name()
  {
    return _name;
  }
  
  @Override
  public int sessionId()
  {
    return _sessionId;
  }
  
  @Override
  public String toString()
  {
    return "Animals Call salutes the core <"+_name+"> with session <"+_sessionId+">";
  }
}
