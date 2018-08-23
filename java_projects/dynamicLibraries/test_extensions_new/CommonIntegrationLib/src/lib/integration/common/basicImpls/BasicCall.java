package lib.integration.common.basicImpls;

import lib.integration.common.interfaces.ICall;

public class BasicCall implements ICall
{
  private final String _name;
  private final int _sessionId;
  public int val;
  
  public BasicCall(String n_name, int n_sessionId, int n_val)
  {
    _name = n_name;
    _sessionId = n_sessionId;
    val = n_val;
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
    return "Common Call <"+_name+"> with session <"+_sessionId+"> and val <"+val+">";
  }
}
