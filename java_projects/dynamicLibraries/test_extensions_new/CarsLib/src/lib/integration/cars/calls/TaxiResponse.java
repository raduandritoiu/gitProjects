package lib.integration.cars.calls;

import lib.integration.common.interfaces.ICall;
import lib.integration.common.interfaces.ICallResponse;

public class TaxiResponse implements ICallResponse
{
  private final String _name;
  private final int _sessionId;
  private final String callName;
  
  public TaxiResponse(ICall call)
  {
    _name = "GET_A_TAXI";
    if (call != null)
    {
      _sessionId = call.sessionId();
      callName = call.name();
    }
    else
    {
      _sessionId = -1;
      callName = "none";
    }
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
    return "Cars Call Response <"+_name+"> in response to Call <"+callName+"> with session <"+_sessionId+">";
  }
}
