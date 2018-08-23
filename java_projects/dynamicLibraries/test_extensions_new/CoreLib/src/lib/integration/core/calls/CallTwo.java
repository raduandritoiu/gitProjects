package lib.integration.core.calls;

import lib.integration.common.interfaces.ICall;

public class CallTwo implements ICall
{
  private final String _name;
  private final int _sessionId;
  
  public CallTwo(int n_sessionId)
  {
    _name = "CALL_WITH_A_VEEERY_BIG_NAME";
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
