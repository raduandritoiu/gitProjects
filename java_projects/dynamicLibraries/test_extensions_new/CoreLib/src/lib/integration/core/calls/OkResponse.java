package lib.integration.core.calls;

import lib.integration.common.interfaces.ICall;
import lib.integration.common.interfaces.ICallResponse;

public class OkResponse implements ICallResponse
{
  private final String _name;
  private final int _sessionId;
  private final String callName;
  
  
  public OkResponse(String n_name, ICall call)
  {
    _name = n_name;
    _sessionId = call.sessionId();
    callName = call.name();
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
    return "Core Response <"+_name+"> in response to Call <"+callName+"> with session <"+_sessionId+">";
  }
}