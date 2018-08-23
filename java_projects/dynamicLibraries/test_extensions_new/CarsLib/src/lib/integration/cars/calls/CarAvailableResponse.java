package lib.integration.cars.calls;

import lib.integration.common.interfaces.ICall;
import lib.integration.common.interfaces.ICallResponse;


public class CarAvailableResponse implements ICallResponse
{
  private final String _name;
  private final int _sessionId;
  private final String callName;
  
  public CarAvailableResponse(ICall call)
  {
    _name = "HAVE_AVAILABLE";
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
    return "Cars Call Response <"+_name+"> like 4 cars, in response to Call <"+callName+"> with session <"+_sessionId+">";
  }
}
