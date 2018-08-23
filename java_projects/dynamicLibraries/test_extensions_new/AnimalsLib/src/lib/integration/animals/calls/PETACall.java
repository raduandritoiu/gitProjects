package lib.integration.animals.calls;

import lib.integration.common.interfaces.ICall;

public class PETACall implements ICall
{
  private final String _name;
  private final int _sessionId;
  
  public PETACall(int n_sessionId)
  {
    _name = "PETA";
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
    return "Animals Calls the core <"+_name+"> with session <"+_sessionId+">";
  }
}
