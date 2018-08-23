package lib.integration.common.basicImpls;

import lib.integration.common.interfaces.ICallResponse;

public class BasicCallResponse implements ICallResponse
{
  private final String _name;
  private final int _sessionId;
  private final String callName;
  public int val;
  
  public BasicCallResponse(String n_name, String n_callName, int n_sessionId, int n_val)
  {
    _name = n_name;
    _sessionId = n_sessionId;
    callName = n_callName;
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
    return "Common Call Response <"+_name+"> with val <"+val+"> in response to Call <"+callName+"> with session <"+_sessionId+">";
  }
}
