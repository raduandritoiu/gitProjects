package lib.integration.core.calls;

import lib.integration.common.interfaces.ICall;

public class CallOne implements ICall
{
  private final String _name;
  private final int _sessionId;
  
  public CallOne(int n_sessionId)
  {
    _name = "CALL_ONE";
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
    return "Core <"+_name+"> with session <"+_sessionId+">";
  }
}
